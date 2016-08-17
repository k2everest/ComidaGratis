package br.com.cafebinario.register.test;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.cafebinario.exception.ConfirmUserException;
import br.com.cafebinario.main.Main;
import br.com.cafebinario.register.config.JUnitConfig;
import br.com.cafebinario.register.facade.UserAccountRegisterFacade;
import br.com.cafebinario.register.vo.result.ResultVO;
import br.com.cafebinario.register.vo.result.builder.ResultVOBuilder;
import br.com.cafebinario.register.vo.result.user.UserListResultVO;
import br.com.cafebinario.register.vo.user.NewUserVO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = { Main.class })
@ContextConfiguration(classes = { JUnitConfig.class })
public class RegisterUserControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@MockBean
	private UserAccountRegisterFacade registerFacade;

	private NewUserVO userVO;
	private UserListResultVO userListResultVO;

	private String secureKey = "123456";

	@Before
	public void setup() {
		this.userVO = NewUserVO.createUserVOJUnitValidTest();
		this.userListResultVO = UserListResultVO.createUserListResultVOJUnitTest();
	}

	@Test
	public void registerSUCCESS() {
		BDDMockito.doNothing().when(this.registerFacade).newUser(userVO);

		ResponseEntity<ResultVO> resultVONewUser = testRestTemplate.postForEntity("/user/new", this.userVO,
				ResultVO.class);
		Assert.assertEquals(resultVONewUser.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void confirmSUCCESS() throws ConfirmUserException {
		BDDMockito.doNothing().doThrow(new ConfirmUserException()).when(registerFacade).confirmUser("nickBla", secureKey);
		testRestTemplate.put("/user/confirm?nick=nickBla&secureKey=" + secureKey, null);
	}

	@Test
	public void lastedTenSUCCESS() throws ConfirmUserException {
		BDDMockito.given(this.registerFacade.lastTen()).willReturn(userListResultVO.getUserList());

		ResponseEntity<UserListResultVO> resultVOLastTen = testRestTemplate.getForEntity("/user/lastTen",
				UserListResultVO.class);

		Assert.assertEquals(HttpStatus.OK, resultVOLastTen.getStatusCode());
		Assert.assertEquals(userListResultVO.getUserList(), resultVOLastTen.getBody().getUserList());
	}
}