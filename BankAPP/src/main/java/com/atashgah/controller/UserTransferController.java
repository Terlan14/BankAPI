package com.atashgah.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atashgah.exception.AccountDeactiveException;
import com.atashgah.exception.AccountNotFoundException;
import com.atashgah.exception.InsufficientBalanceException;
import com.atashgah.exception.UserNotFoundException;
import com.atashgah.model.BankAccount;
import com.atashgah.model.Transfer;
import com.atashgah.model.User;
import com.atashgah.service.TransferService;
import com.atashgah.service.UserBankAccountService;
import com.atashgah.service.UserService;

@RestController
@RequestMapping("/transfer")
public class UserTransferController {
	
	@Autowired
	TransferService transferService;
	
	@Autowired
	UserService userService;
	
	
	//it performs specific user's bank account transfers.
	@PostMapping("/{pin}")
	public void transferBetweenUserAccounts(@PathVariable String pin,Long fromAccount,Long toAccount,double amount) throws UserNotFoundException, InsufficientBalanceException {
		User user=userService.getUserByPin(pin);
		transferService.transferBetweenUserAccounts(user,fromAccount,toAccount,amount);
		
	}
	//it performs both specific and different user's bank account transfers.
	@PostMapping("/{fromAccount}/{toAccount}")
	public ResponseEntity<String> transferFromUserToUser(@PathVariable Long fromAccount,@PathVariable Long toAccount,double amount) {
		try {
			transferService.transferFromAccounntToAccount(fromAccount,toAccount,amount);
			return new ResponseEntity<>("Transfer successfull",HttpStatus.OK);
		}
		catch (AccountNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
		catch(AccountDeactiveException ex) {
			return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
		}
		catch (InsufficientBalanceException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>("An error occurred during the transfer", HttpStatus.INTERNAL_SERVER_ERROR);
        }
		
		
	}
	@GetMapping("/allTransfers/{pin}")
	public List<Transfer>gettAllTransfers(@PathVariable String pin) throws UserNotFoundException{
		User user=userService.getUserByPin(pin);
		return transferService.getAllTransfers(user);
	}
	@GetMapping("/{pin}/to")
	public List<Transfer>getTransferToUser(@PathVariable String pin) throws UserNotFoundException{
		User user=userService.getUserByPin(pin);
		return transferService.getTransferToUser(user);
	}
	@GetMapping("/{pin}/from")
	public List<Transfer>getTransferFromUser(@PathVariable String pin) throws UserNotFoundException{
		User user=userService.getUserByPin(pin);
		return transferService.getTransferFromUser(user);
	}
	


}
