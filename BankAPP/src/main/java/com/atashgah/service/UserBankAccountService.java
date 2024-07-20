package com.atashgah.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atashgah.model.BankAccount;
import com.atashgah.model.BankAccount.Status;
import com.atashgah.model.User;
import com.atashgah.repository.BankAccountRepository;

@Service
public class UserBankAccountService {
	
	@Autowired
	private BankAccountRepository bankAccountRepository;

	public List<BankAccount> getUserBankAccounts(User user) {
        return bankAccountRepository.findByUser(user);
    }
	public Optional<BankAccount> getUserSpecificBankAccount(User user, Long accountId) {
        return bankAccountRepository.findByUserAndId(user, accountId);
    }
	public BankAccount addBankAccount(User user, BankAccount bankAccount) {
		// TODO Auto-generated method stub
		bankAccount.setUser(user);
		bankAccount.setBalance(300.00);
		System.out.println("Initial balance : "+bankAccount.getBalance());
		return bankAccountRepository.save(bankAccount);
	}
	public void deleteUserBankAccount(User user, Long id) {
		
		Optional<BankAccount> bankAccountOptional = bankAccountRepository.findByUserAndId(user, id);
        bankAccountOptional.ifPresent(bankAccountRepository::delete);
		
	}
	public BankAccount setUserBankAccountActive(User user, Long id) {
		// TODO Auto-generated method stub
		Optional<BankAccount>bankAccountOptional=bankAccountRepository.findByUserAndId(user, id);
		if(bankAccountOptional.isPresent()) {
			BankAccount bankAccount=bankAccountOptional.get();
			bankAccount.setStatus(BankAccount.Status.ACTIVE);
			return bankAccountRepository.save(bankAccount);
		}
		return null;
		
	}
	public Optional<BankAccount>getBankAccount(Long id){
		return bankAccountRepository.findById(id);
	}
	public List<BankAccount> getUserActiveBankAccounts(User user) {
		// TODO Auto-generated method stub
		
		return bankAccountRepository.findByUserAndStatus(user, Status.ACTIVE);
	}
	
	

}
