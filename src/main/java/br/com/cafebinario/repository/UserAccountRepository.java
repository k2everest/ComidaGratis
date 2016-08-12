package br.com.cafebinario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.cafebinario.entiry.UserAccount;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, String>{

	@Query("select u from UserAccount u where u.secureKey = ?1")
	UserAccount findUserBySecureKey(final String secureKey);

	@Query("select u from UserAccount u where u.nick = ?1 or u.secureKey = ?2")
	UserAccount findByNickOrSecureKey(final String nick, final String secureKey);

	@Query("select u from UserAccount u where u.email = ?1 or u.nick = ?2")
	UserAccount existUserByEmailOrNick(final String email, final String nick);
}
