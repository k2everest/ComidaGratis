package br.com.cafebinario.register.rules.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.cafebinario.entiry.UserAccount;
import br.com.cafebinario.main.Main;
import br.com.cafebinario.register.rules.user.FindLastTenUsersRules;
import br.com.cafebinario.repository.UserAccountRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Main.class })
public class FindLastTenUsersTest {

	@Autowired
	private FindLastTenUsersRules findLastTenUsersRules;

	@MockBean
	private UserAccountRepository userAccountRepository;

	@Test
	public void test() {
		final PageRequest pageRequest = new PageRequest(1, 10, sortByUserAlterDate());

		BDDMockito.given(userAccountRepository.findAll(pageRequest)).willReturn(null);
		List<UserAccount> userAccountList = findLastTenUsersRules.get();
		Assert.assertEquals(new ArrayList<UserAccount>(), userAccountList);
	}

	private Sort sortByUserAlterDate() {
		return new Sort(UserAccount.sortByUserAlterDate());
	}
}