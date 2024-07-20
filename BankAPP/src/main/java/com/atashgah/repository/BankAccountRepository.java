package com.atashgah.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atashgah.model.BankAccount;
import com.atashgah.model.User;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long>{
	List<BankAccount> findByUser(User user);
    Optional<BankAccount> findByUserAndId(User user, Long id);
    List<BankAccount>findByUserAndStatus(User user,BankAccount.Status status);
    Optional<BankAccount> findById(Long id);

}
