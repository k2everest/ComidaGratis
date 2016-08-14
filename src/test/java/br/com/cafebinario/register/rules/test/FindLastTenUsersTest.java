package br.com.cafebinario.register.rules.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.cafebinario.entity.UserAccount;
import br.com.cafebinario.main.Main;
import br.com.cafebinario.register.rules.domain.CreateDomainRules;
import br.com.cafebinario.register.rules.domain.PersistDomainRules;
import br.com.cafebinario.register.rules.user.CreateUserRules;
import br.com.cafebinario.register.rules.user.FindLastTenUsersRules;
import br.com.cafebinario.register.rules.user.PersistUserRules;
import br.com.cafebinario.register.vo.domain.NewDomainVO;
import br.com.cafebinario.register.vo.user.NewUserVO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Main.class })
public class FindLastTenUsersTest {

	@Autowired
	private FindLastTenUsersRules findLastTenUsersRules;

	@Autowired
	private PersistUserRules persistUserRules;
	
	@Autowired
	private PersistDomainRules persistDomainRules;
	
	@Autowired
	private CreateDomainRules createDomainRules;
	
	@Autowired
	private CreateUserRules createUserRules;

	@Test
	@Transactional
	public void test() {
		
		NewDomainVO domainVO = new NewDomainVO();
		domainVO.setDescription("blablavla");
		domainVO.setDomain("cafebinario");
		domainVO.setEmailOwner("lalala@gmail.com");

		persistDomainRules.accept(createDomainRules.apply(domainVO));
		
		List<UserAccount> list = getEntities();
		list.forEach(user -> persistUserRules.accept(user));
		List<UserAccount> userAccountList = findLastTenUsersRules.get();
		Assert.assertEquals(10, userAccountList.size());
	}

	private List<UserAccount> getEntities() {
		List<UserAccount> userAccount = new ArrayList<>();
		
		userAccount.add(createUserRules.apply(NewUserVO.createUserVOJUnitValidTest("1-alexandre.msl@gmail.com", "ale1", "abcd")));
		userAccount.add(createUserRules.apply(NewUserVO.createUserVOJUnitValidTest("2-alexandre.msl@gmail.com", "ale2", "abcd")));
		userAccount.add(createUserRules.apply(NewUserVO.createUserVOJUnitValidTest("3-alexandre.msl@gmail.com", "ale3", "abcd")));
		userAccount.add(createUserRules.apply(NewUserVO.createUserVOJUnitValidTest("4-alexandre.msl@gmail.com", "ale4", "abcd")));
		userAccount.add(createUserRules.apply(NewUserVO.createUserVOJUnitValidTest("5-alexandre.msl@gmail.com", "ale5", "abcd")));
		userAccount.add(createUserRules.apply(NewUserVO.createUserVOJUnitValidTest("6-alexandre.msl@gmail.com", "ale6", "abcd")));
		userAccount.add(createUserRules.apply(NewUserVO.createUserVOJUnitValidTest("7-alexandre.msl@gmail.com", "ale7", "abcd")));
		userAccount.add(createUserRules.apply(NewUserVO.createUserVOJUnitValidTest("8-alexandre.msl@gmail.com", "ale8", "abcd")));
		userAccount.add(createUserRules.apply(NewUserVO.createUserVOJUnitValidTest("9-alexandre.msl@gmail.com", "ale9", "abcd")));
		userAccount.add(createUserRules.apply(NewUserVO.createUserVOJUnitValidTest("10-alexandre.msl@gmail.com", "ale10", "abcd")));
		userAccount.add(createUserRules.apply(NewUserVO.createUserVOJUnitValidTest("11-alexandre.msl@gmail.com", "ale11", "abcd")));
		userAccount.add(createUserRules.apply(NewUserVO.createUserVOJUnitValidTest("12-alexandre.msl@gmail.com", "ale12", "abcd")));
		userAccount.add(createUserRules.apply(NewUserVO.createUserVOJUnitValidTest("13-alexandre.msl@gmail.com", "ale13", "abcd")));
		userAccount.add(createUserRules.apply(NewUserVO.createUserVOJUnitValidTest("14-alexandre.msl@gmail.com", "ale14", "abcd")));
		userAccount.add(createUserRules.apply(NewUserVO.createUserVOJUnitValidTest("15-alexandre.msl@gmail.com", "ale15", "abcd")));
		userAccount.add(createUserRules.apply(NewUserVO.createUserVOJUnitValidTest("16-alexandre.msl@gmail.com", "ale16", "abcd")));
		userAccount.add(createUserRules.apply(NewUserVO.createUserVOJUnitValidTest("17-alexandre.msl@gmail.com", "ale17", "abcd")));
		userAccount.add(createUserRules.apply(NewUserVO.createUserVOJUnitValidTest("18-alexandre.msl@gmail.com", "ale18", "abcd")));
		userAccount.add(createUserRules.apply(NewUserVO.createUserVOJUnitValidTest("19-alexandre.msl@gmail.com", "ale19", "abcd")));
		userAccount.add(createUserRules.apply(NewUserVO.createUserVOJUnitValidTest("20-alexandre.msl@gmail.com", "ale20", "abcd")));
		return userAccount;
	}
}