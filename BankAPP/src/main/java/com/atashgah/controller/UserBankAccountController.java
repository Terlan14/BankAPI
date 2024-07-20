package com.atashgah.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	@GetMapping("/{pin}")
	public List<BankAccount> getUserBankAccountsByPin(@PathVariable String pin) throws UserNotFoundException {
		User user=userService.getUserByPin(pin);
		return userBankAccountService.getUserBankAccounts(user);
	}
	@GetMapping("/{pin}/{id}")
	public Optional<BankAccount> getUserBankAccountByPin(@PathVariable String pin,@PathVariable Long id) throws UserNotFoundException {
		
		User user=userService.getUserByPin(pin);
		return userBankAccountService.getUserSpecificBankAccount(user, id);
	}
	@PostMapping("/add/{pin}")
	public BankAccount addBankAccount(@PathVariable String pin,@RequestBody BankAccount bankAccount) throws UserNotFoundException {
		User user=userService.getUserByPin(pin);
		return userBankAccountService.addBankAccount(user,bankAccount);
	}
	
	@DeleteMapping("{pin}/{id}")
	public void deleteUserSpecificBankAccount(@PathVariable String pin,@PathVariable Long id) throws UserNotFoundException {
		User user=userService.getUserByPin(pin);
		userBankAccountService.deleteUserBankAccount(user,id);
	}
	@PostMapping("{pin}/{id}")
	public BankAccount setUserSpecificBankAccountActive(@PathVariable String pin,@PathVariable Long id) throws UserNotFoundException {
		User user=userService.getUserByPin(pin);
		return userBankAccountService.setUserBankAccountActive(user,id);
	}
	
	@GetMapping("/active/{pin}")
	public List<BankAccount>getUserActiveBankAccounts(@PathVariable String pin) throws UserNotFoundException{
		User user=userService.getUserByPin(pin);
		return userBankAccountService.getUserActiveBankAccounts(user);
	}

}
