package com.atashgah.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atashgah.dto.BankDTO;
import com.atashgah.exception.UserNotFoundException;
import com.atashgah.model.BankAccount;
import com.atashgah.model.User;
import com.atashgah.service.UserBankAccountService;
import com.atashgah.service.UserService;

@RestController
@RequestMapping("/api/account")
public class UserBankAccountController {
	
	@Autowired
	private UserBankAccountService userBankAccountService;
	@Autowired
	private UserService userService;
	@GetMapping("/getAll/{pin}")
	public List<BankDTO> getUserBankAccountsByPin(@PathVariable String pin) throws UserNotFoundException {
		User user=userService.getUserByPin(pin);
		return userBankAccountService.getUserBankAccounts(user);
	}
	@GetMapping("/getOne/{pin}/{id}")
	public Optional<BankDTO> getUserBankAccountByPin(@PathVariable String pin,@PathVariable Long id) throws Exception {
		
		User user=userService.getUserByPin(pin);
		return userBankAccountService.getUserSpecificBankAccount(user, id);
	}
	@PostMapping("/add/{pin}")
	public BankDTO addBankAccount(@PathVariable String pin,@RequestBody BankDTO bankDTO) throws UserNotFoundException {
		User user=userService.getUserByPin(pin);
		return userBankAccountService.addBankAccount(user,bankDTO);
	}
	
	
	@PostMapping("/setActive/{pin}/{id}")
	public BankDTO setUserSpecificBankAccountActive(@PathVariable String pin,@PathVariable Long id) throws Exception {
		User user=userService.getUserByPin(pin);
		return userBankAccountService.setUserBankAccountActive(user,id);
	}
	
	@GetMapping("/active/{pin}")
	public List<BankDTO>getUserActiveBankAccounts(@PathVariable String pin) throws UserNotFoundException{
		User user=userService.getUserByPin(pin);
		return userBankAccountService.getUserActiveBankAccounts(user);
	}

}
