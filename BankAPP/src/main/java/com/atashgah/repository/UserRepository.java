package com.atashgah.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atashgah.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User getUserByPin(String pin);

	boolean existsByEmail(String email);

	boolean existsByPin(String pin);

	void deleteByPin(String pin);

}
