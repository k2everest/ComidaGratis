package br.com.cafebinario.register;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cafebinario.exception.ConfirmUserException;
import br.com.cafebinario.exception.NotifyException;
import br.com.cafebinario.exception.VerifyExistUserException;
import br.com.cafebinario.register.datamemory.CafebinarioMemory;
import br.com.cafebinario.register.datamemory.SecureMemoryData;
import br.com.cafebinario.register.entiry.UserAccount;
import br.com.cafebinario.register.rules.ConfimUserRegisterRules;
import br.com.cafebinario.register.rules.CreateAuthenticationTokenRules;
import br.com.cafebinario.register.rules.CreateRegisterUrlRules;
import br.com.cafebinario.register.rules.CreateUserRules;
import br.com.cafebinario.register.rules.FindLastTenUsersRules;
import br.com.cafebinario.register.rules.PersistUserRules;
import br.com.cafebinario.register.rules.SendSecureKeyRules;
import br.com.cafebinario.register.rules.UserAuthenticationRules;
import br.com.cafebinario.register.rules.UserToUserVORules;
import br.com.cafebinario.register.vo.NewUserVO;
import br.com.cafebinario.register.vo.UserAuthenticationVO;
import br.com.cafebinario.register.vo.result.AuthenticationResultVO;
import br.com.cafebinario.register.vo.result.ResultVO;
import br.com.cafebinario.register.vo.result.UserListResultVO;
import br.com.cafebinario.register.vo.result.builder.ResultVOBuilder;

@Service
public class RegisterFacade {

	@Autowired
	private CreateUserRules createUserRules;

	@Autowired
	private ConfimUserRegisterRules confimUserRegisterRules;

	@Autowired
	private FindLastTenUsersRules findLastTenUsersRules;

	@Autowired
	private PersistUserRules persistUserRules;

	@Autowired
	private UserToUserVORules userToUserVORules;

	@Autowired
	private SendSecureKeyRules sendSecureKeyRules;

	@Autowired
	private CreateRegisterUrlRules createRegisterUrlRules;

	@Autowired
	private UserAuthenticationRules userAuthenticationRules;

	@Autowired
	private CreateAuthenticationTokenRules createAuthenticationTokenRules;

	@Transactional
	public ResultVO newUser(final NewUserVO userVO) {
		try {
			final UserAccount user = createUserRules.apply(userVO);

			/**
			 * TODO esse método devera virar uma classe para persistir gerar a
			 * url e enviar por e-mail em outro processo e tratar retentativa em caso de falha...
			 */
			dispacherAsync(user);
			return ResultVOBuilder.SUCCESS();
		} catch (VerifyExistUserException e) {
			return ResultVOBuilder.ERROR_USER_HAS_EXIST();
		} catch (NotifyException e) {
			return ResultVOBuilder.ERROR_SEND_SECURE_KEY();
		}
	}

	private void dispacherAsync(final UserAccount user) {
		new Thread(()->{
			persistUserRules.accept(user);
			final String url = createRegisterUrlRules.apply(user.getSecureKey());
			sendSecureKeyRules.accept(user.getEmail(), url);
		}).start();
	}

	@Transactional
	public void confirmUser(final String secureKey) throws ConfirmUserException {
		final UserAccount user = confimUserRegisterRules.apply(secureKey);
		persistUserRules.accept(user);
	}

	public UserListResultVO lastTen() {
		final List<UserAccount> userList = findLastTenUsersRules.get();
		final List<NewUserVO> userVOList = new ArrayList<>(userList.size());

		userList.forEach(user -> {
			NewUserVO userVO = userToUserVORules.apply(user);
			userVOList.add(userVO);
		});

		return new UserListResultVO(ResultVOBuilder.SUCCESS(), userVOList);
	}

	public AuthenticationResultVO auth(UserAuthenticationVO userAuthenticationVO) {
		try {
			userAuthenticationRules.accept(userAuthenticationVO);
			SecureMemoryData secureMemoryData = createAuthenticationTokenRules.apply(userAuthenticationVO);

			//TODO avaliar se essa forma de acesso é a melhor...
			CafebinarioMemory.getInstance().put(secureMemoryData);
			return new AuthenticationResultVO(secureMemoryData.getPair().getFirst());
		} catch (RuntimeException e) {
			return new AuthenticationResultVO();
		}
	}

	public UserAuthenticationVO getUserAuthenticationVOByToken(String token) {
		return CafebinarioMemory.getInstance().get(token);
	}
}