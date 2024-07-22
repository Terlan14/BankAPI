package com.atashgah.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atashgah.model.Transfer;
import com.atashgah.model.User;

public interface TransferRepository extends JpaRepository<Transfer, Long>{
	List<Transfer> findByToAccount_User(User user);
	List<Transfer> findByFromAccount_User(User user);
	void deleteById(Long id);
	//List<Transfer>findByAccountUser(User user);

}
