package com.atashgah.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.atashgah.dto.UserDTO;
import com.atashgah.dto.UserMapper;
import com.atashgah.dto.UserRegistrationDTO;
import com.atashgah.exception.PinSizeException;
import com.atashgah.exception.UserAlreadyExistsException;
import com.atashgah.exception.UserNotFoundException;
import com.atashgah.model.User;
import com.atashgah.repository.BankAccountRepository;
import com.atashgah.repository.TransferRepository;
import com.atashgah.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TransferRepository transferRepository;
	
	@Autowired
	private BankAccountRepository bankAccountRepository;
	
		

	public void registerUser(UserRegistrationDTO user) throws UserAlreadyExistsException {
		// TODO Auto-generated method stub
		if(userRepository.existsByPin(user.getPin())) {
			throw new UserAlreadyExistsException("PIN is already in use");
		}
		if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("Email is already in use");
        }
		if(user.getPin().length()!=7) {
			throw new PinSizeException("Pin size should be 7 characters");
		}
		var bCryptEncoder=new BCryptPasswordEncoder();
		user.setPassword(bCryptEncoder.encode(user.getPassword()));
		User savedUser=UserMapper.toEntity(user);
		userRepository.save(savedUser);
	}

	public User getUserByPin(String pin) throws UserNotFoundException {
		// TODO Auto-generated method stub
		 User user=userRepository.getUserByPin(pin);
		 if(user==null) {
			 throw new UserNotFoundException("User not found with pin: "+pin);
		 }
		 return user;
		 
	}

	@Transactional
	public void deleteUser(String pin) throws UserNotFoundException {
		// TODO Auto-generated method stub
		
		User user=userRepository.getUserByPin(pin);
		if(user==null) {
			throw new UserNotFoundException("User not found with pin: "+pin);
		}
		
		
		userRepository.deleteByPin(pin);
		

		
	}

	

	
}
