package com.atashgah.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.atashgah.model.User;
import com.atashgah.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	
	
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String pin) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		 User user = userRepository.getUserByPin(pin);
		 
	     if(user==null) {
	    	 throw new UsernameNotFoundException("User not found with PIN: "+pin);
	     }
	     return user;
	}
	

}
