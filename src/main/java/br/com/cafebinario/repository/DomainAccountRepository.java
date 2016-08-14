package br.com.cafebinario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cafebinario.entity.DomainAccount;

@Repository
public interface DomainAccountRepository extends JpaRepository<DomainAccount, String>{

}
