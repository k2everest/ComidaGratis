package br.com.cafebinario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.cafebinario.entity.UserAccount;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, String>{

	@Query("select u from UserAccount u where u.nick = ?1 and u.secureKey = ?2")
	UserAccount findUserByNickAndSecureKey(final String nick, final String secureKey);

	@Query("select u from UserAccount u where u.nick = ?1 or u.secureKey = ?2 and u.alterDate is not null")
	UserAccount findByNickOrSecureKey(final String nick, final String secureKey);

	@Query("select u from UserAccount u where u.email = ?1 or u.nick = ?2")
	UserAccount existUserByEmailOrNick(final String email, final String nick);
}