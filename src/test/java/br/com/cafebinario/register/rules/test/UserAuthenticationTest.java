package br.com.cafebinario.register.rules.test;

import java.util.Date;

import javax.crypto.SecretKey;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.cafebinario.entiry.DomainAccount;
import br.com.cafebinario.entiry.UserAccount;
import br.com.cafebinario.main.Main;
import br.com.cafebinario.register.rules.domain.PersistDomainRules;
import br.com.cafebinario.register.rules.user.CreateSecurePasswordRules;
import br.com.cafebinario.register.rules.user.EncriptyDataRules;
import br.com.cafebinario.register.rules.user.UserAuthenticationRules;
import br.com.cafebinario.register.vo.user.NewUserVO;
import br.com.cafebinario.register.vo.user.UserAuthenticationVO;
import br.com.cafebinario.repository.UserAccountRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Main.class })
public class UserAuthenticationTest {

	@Autowired
	private UserAuthenticationRules userAuthenticationRules;
	
	@Autowired
	private UserAccountRepository userAccountRepository;

	@Autowired
	private PersistDomainRules persistDomainRules;

	@Autowired
	private CreateSecurePasswordRules createSecurePasswordRules;

	@Test
	public void test() {

		DomainAccount domain = new DomainAccount();
		domain.setCreateDate(new Date(System.currentTimeMillis()));
		domain.setDescription("default domain");
		domain.setDomain("cafebinario");
		domain.setEmailOwner("alexandre.msl@gmail.com");

		persistDomainRules.accept(domain);

		final NewUserVO newUserVO = NewUserVO.createUserVOJUnitValidTest();

		final String securePassword = createSecurePasswordRules.apply("cafebinario",
				"123456");
		
		final UserAccount userAccount = new UserAccount(newUserVO, "123456", securePassword);
		userAccountRepository.save(userAccount);

		final UserAuthenticationVO userAuthenticationVO = UserAuthenticationVO.create(newUserVO, securePassword);

		userAuthenticationRules.accept(userAuthenticationVO, "123456");
	}
}
