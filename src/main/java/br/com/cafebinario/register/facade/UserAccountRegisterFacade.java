package br.com.cafebinario.register.facade;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.hazelcast.core.ITopic;

import br.com.cafebinario.entity.UserAccount;
import br.com.cafebinario.exception.ConfirmUserException;
import br.com.cafebinario.exception.NotifyException;
import br.com.cafebinario.exception.VerifyExistUserException;
import br.com.cafebinario.register.datamemory.CafebinarioMemory;
import br.com.cafebinario.register.datamemory.SecureMemoryData;
import br.com.cafebinario.register.listener.UserAccountEventListener;
import br.com.cafebinario.register.rules.user.ConfimUserRegisterRules;
import br.com.cafebinario.register.rules.user.CreateAuthenticationTokenRules;
import br.com.cafebinario.register.rules.user.CreateRegisterUrlRules;
import br.com.cafebinario.register.rules.user.CreateUserRules;
import br.com.cafebinario.register.rules.user.DecriptyDataRules;
import br.com.cafebinario.register.rules.user.FindLastTenUsersRules;
import br.com.cafebinario.register.rules.user.SendSecureKeyRules;
import br.com.cafebinario.register.rules.user.UserAuthenticationRules;
import br.com.cafebinario.register.rules.user.UserToUserVORules;
import br.com.cafebinario.register.vo.result.ResultVO;
import br.com.cafebinario.register.vo.result.builder.ResultVOBuilder;
import br.com.cafebinario.register.vo.result.user.AuthenticationResultVO;
import br.com.cafebinario.register.vo.result.user.UserListResultVO;
import br.com.cafebinario.register.vo.user.NewUserVO;
import br.com.cafebinario.register.vo.user.UserAuthenticationVO;

@Service
public class UserAccountRegisterFacade {

	@Autowired
	private CreateUserRules createUserRules;

	@Autowired
	private ConfimUserRegisterRules confimUserRegisterRules;

	@Autowired
	private FindLastTenUsersRules findLastTenUsersRules;

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
	
	@Autowired
	private DecriptyDataRules decriptyDataRules;
	
	@Autowired
	private SecretKey cafeBinarioSecretKey;

	@Autowired
	private ITopic<UserAccount> userAccountTopic;
	
	@Autowired
	private UserAccountEventListener userAccountEventListener;
	
	@Autowired
	private CafebinarioMemory cafebinarioMemory;
	
	public void newUser(final NewUserVO userVO) {
		
		final UserAccount user = createUserRules.apply(userVO);
		final String url = createRegisterUrlRules.apply(user.getSecureKey());
		notify(user, url);
	}

	private void notify(final UserAccount user, String url) {
		userAccountTopic.addMessageListener(userAccountEventListener);
		userAccountTopic.publish(user);
		sendSecureKeyRules.accept(user.getEmail(), url);
	}

	public void confirmUser(final String nick, final String secureKey) throws ConfirmUserException {
		final UserAccount user = confimUserRegisterRules.apply(nick, secureKey);
		userAccountTopic.addMessageListener(userAccountEventListener);
		userAccountTopic.publish(user);
	}

	public List<NewUserVO> lastTen() {
		final List<UserAccount> userList = findLastTenUsersRules.get();
		final List<NewUserVO> userVOList = new ArrayList<>(userList.size());

		userList.forEach(user -> {
			final NewUserVO userVO = userToUserVORules.apply(user);
			userVOList.add(userVO);
		});

		return userVOList;
	}

	public String auth(final UserAuthenticationVO userAuthenticationVO) {
		final String decriptyUserPassword = decriptyDataRules.apply(cafeBinarioSecretKey.getFormat(), userAuthenticationVO.getPassword());
		userAuthenticationRules.accept(userAuthenticationVO, decriptyUserPassword);
		final SecureMemoryData secureMemoryData = createAuthenticationTokenRules.apply(userAuthenticationVO);
		
		cafebinarioMemory.put(secureMemoryData);
		return secureMemoryData.getPair().getFirst();
	}

	public UserAuthenticationVO getUserAuthenticationVOByToken(final String nick, final String token) {
		Assert.hasText(nick);
		Assert.hasLength(token);
		return cafebinarioMemory.get(nick, token);
	}
}