package com.atashgah.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atashgah.exception.AccountDeactiveException;
import com.atashgah.exception.AccountNotFoundException;
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
	public Optional<BankAccount> getUserSpecificBankAccount(User user, Long accountId) throws Exception {
		BankAccount account = bankAccountRepository.findByUserAndId(user,accountId)
                .orElseThrow(() -> new AccountNotFoundException ("Account not found for specific user "+user.getPin()));
        return Optional.of(account);
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
	public BankAccount setUserBankAccountActive(User user, Long id) throws Exception {
		// TODO Auto-generated method stub
		BankAccount bankAccount=bankAccountRepository.findByUserAndId(user, id)
				.orElseThrow(()->new AccountNotFoundException ("Account not found for specific user "+user.getPin()));
		
		if(bankAccount.getStatus().equals(BankAccount.Status.ACTIVE)) {
			throw new Exception("Account is already active");
		}
		bankAccount.setStatus(BankAccount.Status.ACTIVE);
		return bankAccountRepository.save(bankAccount);
		
		
	}
	public Optional<BankAccount>getBankAccount(Long id){
		return bankAccountRepository.findById(id);
	}
	public List<BankAccount> getUserActiveBankAccounts(User user) {
		// TODO Auto-generated method stub
		
		return bankAccountRepository.findByUserAndStatus(user, Status.ACTIVE);
	}
	
	

}
