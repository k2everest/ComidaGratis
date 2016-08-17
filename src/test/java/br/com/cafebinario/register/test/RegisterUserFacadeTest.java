package br.com.cafebinario.register.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.cafebinario.entity.UserAccount;
import br.com.cafebinario.exception.NotifyException;
import br.com.cafebinario.exception.VerifyExistUserException;
import br.com.cafebinario.main.Main;
import br.com.cafebinario.register.facade.UserAccountRegisterFacade;
import br.com.cafebinario.register.rules.user.ConfimUserRegisterRules;
import br.com.cafebinario.register.rules.user.CreateRegisterUrlRules;
import br.com.cafebinario.register.rules.user.CreateSecurePasswordRules;
import br.com.cafebinario.register.rules.user.CreateUserRules;
import br.com.cafebinario.register.rules.user.FindLastTenUsersRules;
import br.com.cafebinario.register.rules.user.PersistUserRules;
import br.com.cafebinario.register.rules.user.SendSecureKeyRules;
import br.com.cafebinario.register.rules.user.UserToUserVORules;
import br.com.cafebinario.register.vo.user.NewUserVO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Main.class })
public class RegisterUserFacadeTest {

	@Autowired
	private UserAccountRegisterFacade registerFacade;

	@MockBean
	private CreateUserRules createUserRules;

	@MockBean
	private ConfimUserRegisterRules confimUserRegisterRules;

	@MockBean
	private FindLastTenUsersRules findLastTenUsersRules;

	@MockBean
	private PersistUserRules persistUserRules;

	@Autowired
	private UserToUserVORules userToUserVORules;

	@MockBean
	private SendSecureKeyRules sendSecureKeyRules;

	@Autowired
	private CreateRegisterUrlRules createRegisterUrl;

	@Autowired
	private CreateSecurePasswordRules createSecurePasswordRules;

	private NewUserVO userVO;

	private String secureKey;

	private List<NewUserVO> expectedUserVOList;
	private List<UserAccount> expectedUserAccountList;

	private UserAccount expectedUserAccount;

	private String expectedTo;

	private String expectedUrl;

	@Before
	public void setup() {
		final NewUserVO newUserVO = NewUserVO.createUserVOJUnitValidTest();
		final String secureKey = "123456";
		final String securePassword = createSecurePasswordRules.apply(newUserVO.getDomain(), newUserVO.getPassword());
		this.expectedUserAccount = new UserAccount(NewUserVO.createUserVOJUnitValidTest(), secureKey, securePassword);

		this.expectedUserAccountList = new ArrayList<>();
		this.expectedUserAccountList.add(expectedUserAccount);
		this.userVO = userToUserVORules.apply(expectedUserAccount);
		this.expectedUserVOList = new ArrayList<>();
		this.expectedUserVOList.add(userVO);

		BDDMockito.doNothing().when(persistUserRules).accept(expectedUserAccount);
		BDDMockito.given(confimUserRegisterRules.apply(userVO.getNick(), secureKey)).willReturn(expectedUserAccount);
		BDDMockito.given(findLastTenUsersRules.get()).willReturn(expectedUserAccountList);

		this.expectedTo = userVO.getEmail();
		this.expectedUrl = createRegisterUrl.apply(secureKey);
	}

	@Test
	public void newUserTest() {
		BDDMockito.given(createUserRules.apply(userVO)).willReturn(expectedUserAccount);
		BDDMockito.doNothing().when(sendSecureKeyRules).accept(expectedTo, expectedUrl);

		registerFacade.newUser(userVO);
	}

	@Test(expected=VerifyExistUserException.class)
	public void userHasExistTest() {
		BDDMockito.doThrow(VerifyExistUserException.class).when(createUserRules).apply(userVO);
		registerFacade.newUser(userVO);
	}

	@Test(expected=NotifyException.class)
	public void sendSecureKeyTest() {
		BDDMockito.given(createUserRules.apply(userVO)).willReturn(expectedUserAccount);
		BDDMockito.doThrow(NotifyException.class).when(sendSecureKeyRules).accept(expectedTo, expectedUrl);
		registerFacade.newUser(userVO);
	}

	@Test
	public void confirmUserTest() {
		registerFacade.confirmUser(userVO.getNick(), secureKey);
		Assert.assertTrue(true);
	}

	@Test
	public void lastTenTest() {
		BDDMockito.given(findLastTenUsersRules.get()).willReturn(expectedUserAccountList);
		final List<NewUserVO> newUserListVO = registerFacade.lastTen();
		Assert.assertEquals(expectedUserVOList, newUserListVO);
	}
}
