package br.com.cafebinario.register.rules.test;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.cafebinario.entiry.DomainAccount;
import br.com.cafebinario.entiry.UserAccount;
import br.com.cafebinario.main.Main;
import br.com.cafebinario.register.rules.domain.PersistDomainRules;
import br.com.cafebinario.register.rules.user.CreateSecurePasswordRules;
import br.com.cafebinario.register.rules.user.PersistUserRules;
import br.com.cafebinario.register.vo.user.NewUserVO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Main.class })
public class PersistUserTest {

	@Autowired
	private PersistUserRules persistUserRules;

	@Autowired
	private PersistDomainRules persistDomainRules;

	@Autowired
	private CreateSecurePasswordRules createSecurePasswordRules;

	@Test
	public void testHandle() {
		DomainAccount domain = new DomainAccount();
		domain.setCreateDate(new Date(System.currentTimeMillis()));
		domain.setDescription("default domain");
		domain.setDomain("cafebinario");
		domain.setEmailOwner("alexandre.msl@gmail.com");

		persistDomainRules.accept(domain);

		NewUserVO newUserVO = NewUserVO.createUserVOJUnitValidTest();
		String secureKey = "123456";
		String securePassword = createSecurePasswordRules.apply(newUserVO.getDomain(), newUserVO.getPassword());

		final UserAccount user = new UserAccount(NewUserVO.createUserVOJUnitValidTest(), secureKey, securePassword);
		persistUserRules.accept(user);
	}

	@Test(expected = JpaSystemException.class)
	public void testConstraint() {
		final UserAccount user = new UserAccount(NewUserVO.createUserVOJUnitInvalidTest(), null, null);
		persistUserRules.accept(user);
	}
}