package com.atashgah.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atashgah.dto.TransferDTO;
import com.atashgah.dto.UserDTO;
import com.atashgah.exception.InsufficientBalanceException;
import com.atashgah.exception.UserNotFoundException;
import com.atashgah.exception.ZeroOrNegativeTransferException;
import com.atashgah.model.Transfer;
import com.atashgah.model.User;
import com.atashgah.service.TransferService;
import com.atashgah.service.UserService;

@RestController
@RequestMapping("/api/transfer")
public class UserTransferController {
	
	@Autowired
	TransferService transferService;
	
	@Autowired
	UserService userService;
	
	
	//it performs specific user's bank account transfers.
	@PostMapping("/{pin}")
	public ResponseEntity<String> transferBetweenUserAccounts(@PathVariable String pin,Long fromAccount,Long toAccount,double amount) throws UserNotFoundException, InsufficientBalanceException, ZeroOrNegativeTransferException {
		User user=userService.getUserByPin(pin);
		transferService.transferBetweenUserAccounts(user,fromAccount,toAccount,amount);
		return new ResponseEntity<>("Transfer successfull",HttpStatus.OK);
		
	}
	//it performs both specific and different user's bank account transfers.
	@PostMapping("/{fromAccount}/{toAccount}")
	public ResponseEntity<String> transferFromUserToUser(@PathVariable Long fromAccount,@PathVariable Long toAccount,double amount) throws InsufficientBalanceException, ZeroOrNegativeTransferException {
		
		transferService.transferFromAccounntToAccount(fromAccount,toAccount,amount);
		return new ResponseEntity<>("Transfer successfull",HttpStatus.OK);

		
	}
	@GetMapping("/allTransfers/{pin}")
	public List<TransferDTO> gettAllTransfers(@PathVariable String pin) throws UserNotFoundException{
		User user=userService.getUserByPin(pin);
		return transferService.getAllTransfers(user);
	}
	@GetMapping("/{pin}/to")
	public List<TransferDTO>getTransferToUser(@PathVariable String pin) throws UserNotFoundException{
		User user=userService.getUserByPin(pin);
		return transferService.getTransferToUser(user);
	}
	@GetMapping("/{pin}/from")
	public List<TransferDTO>getTransferFromUser(@PathVariable String pin) throws UserNotFoundException{
		User user=userService.getUserByPin(pin);
		return transferService.getTransferFromUser(user);
	}
	
//	@DeleteMapping("/{id}")
//	public void deleteTransfer(@PathVariable Long id) throws Exception {
//		transferService.deleteTransfer(id);
//	}


}
