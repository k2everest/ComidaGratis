package br.com.cafebinario.register.test;

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
import br.com.cafebinario.register.RegisterFacade;
import br.com.cafebinario.register.config.JUnitConfig;
import br.com.cafebinario.register.vo.NewUserVO;
import br.com.cafebinario.register.vo.result.ResultVO;
import br.com.cafebinario.register.vo.result.UserListResultVO;
import br.com.cafebinario.register.vo.result.builder.ResultVOBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = { Main.class })
@ContextConfiguration(classes = { JUnitConfig.class })
public class RegisterUserControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@MockBean
	private RegisterFacade registerFacade;

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
		BDDMockito.given(this.registerFacade.newUser(userVO)).willReturn(ResultVOBuilder.SUCCESS());

		ResponseEntity<ResultVO> resultVONewUser = testRestTemplate.postForEntity("/register/new", this.userVO,
				ResultVO.class);
		Assert.assertEquals(resultVONewUser.getStatusCode(), HttpStatus.OK);
		Assert.assertEquals(resultVONewUser.getBody(), ResultVOBuilder.SUCCESS());
	}

	@Test
	public void confirmSUCCESS() throws ConfirmUserException {
		BDDMockito.doNothing().doThrow(new ConfirmUserException()).when(registerFacade).confirmUser(secureKey);
		testRestTemplate.put("/register/confirm?secureKey=" + secureKey, null);
	}

	@Test
	public void lastedTenSUCCESS() throws ConfirmUserException {
		BDDMockito.given(this.registerFacade.lastTen()).willReturn(userListResultVO);

		ResponseEntity<UserListResultVO> resultVOLastTen = testRestTemplate.getForEntity("/register",
				UserListResultVO.class);

		Assert.assertEquals(resultVOLastTen.getStatusCode(), HttpStatus.OK);
		Assert.assertEquals(resultVOLastTen.getBody().getResult(), ResultVOBuilder.SUCCESS());
		Assert.assertEquals(resultVOLastTen.getBody().getUserList(), userListResultVO.getUserList());
	}
}