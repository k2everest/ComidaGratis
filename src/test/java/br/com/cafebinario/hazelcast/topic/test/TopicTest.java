package br.com.cafebinario.hazelcast.topic.test;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hazelcast.core.ITopic;

import br.com.cafebinario.entity.DomainAccount;
import br.com.cafebinario.entity.UserAccount;
import br.com.cafebinario.main.Main;
import br.com.cafebinario.register.listener.DomainAccountEventListener;
import br.com.cafebinario.register.listener.UserAccountEventListener;
import br.com.cafebinario.register.rules.user.CreateSecurePasswordRules;
import br.com.cafebinario.register.vo.user.NewUserVO;
import br.com.cafebinario.repository.UserAccountRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Main.class })
public class TopicTest {

	@Autowired
	private ITopic<UserAccount> userAccountTopic;
	
	@Autowired
	private ITopic<DomainAccount> domainAccountTopic;
	
	@Autowired
	private CreateSecurePasswordRules createSecurePasswordRules;
	
	@Autowired
	private UserAccountRepository userAccountRepository;
	
	@Autowired
	private DomainAccountEventListener domainAccountEventListener;
	
	@Autowired
	private UserAccountEventListener userAccountEventListener;
	
	@Test
	public void test(){
		DomainAccount domainAccount = new DomainAccount();
		domainAccount.setCreateDate(new Date(System.currentTimeMillis()));
		domainAccount.setDescription("default domain");
		domainAccount.setDomain("cafebinario");
		domainAccount.setEmailOwner("alexandre.msl@gmail.com");
		domainAccountTopic.addMessageListener(domainAccountEventListener);
		domainAccountTopic.publish(domainAccount);
		
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		NewUserVO newUserVO = NewUserVO.createUserVOJUnitValidTest();
		
		String securePassword = createSecurePasswordRules.apply(newUserVO.getDomain(), newUserVO.getEmail());
		UserAccount userAccount = new UserAccount(newUserVO, "122456", securePassword);
		userAccountTopic.addMessageListener(userAccountEventListener);
		userAccountTopic.publish(userAccount);

		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Assert.assertEquals(1, userAccountRepository.count());
	}
}