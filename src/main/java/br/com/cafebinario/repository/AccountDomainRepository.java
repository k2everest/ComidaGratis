package br.com.cafebinario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cafebinario.entiry.DomainAccount;

@Repository
public interface AccountDomainRepository extends JpaRepository<DomainAccount, String>{

}
