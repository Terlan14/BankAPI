package com.atashgah.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atashgah.exception.AccountDeactiveException;
import com.atashgah.exception.AccountNotFoundException;
import com.atashgah.exception.InsufficientBalanceException;
import com.atashgah.exception.UserNotFoundException;
import com.atashgah.model.BankAccount;
import com.atashgah.model.BankAccount.Status;
import com.atashgah.model.Transfer;
import com.atashgah.model.User;
import com.atashgah.repository.BankAccountRepository;
import com.atashgah.repository.TransferRepository;

@Service
public class TransferService {
	
	@Autowired
	private TransferRepository transferRepository;
	
	@Autowired
	BankAccountRepository bankAccountRepository;
	

	
	@Autowired
	UserBankAccountService userBankAccountService;

	public void transferBetweenUserAccounts(User user, Long fromAccountId, Long toAccountId,Double amount) throws InsufficientBalanceException, UserNotFoundException {
		// TODO Auto-generated method stub
		Optional<BankAccount>fromAccountOpt=bankAccountRepository.findByUserAndId(user, fromAccountId);
		Optional<BankAccount>toAccountOpt=bankAccountRepository.findByUserAndId(user, toAccountId);
		
		if(fromAccountOpt.isPresent()) {
			BankAccount fromAccount = fromAccountOpt.get();
            BankAccount toAccount = toAccountOpt.get();
            if(fromAccount.getStatus()==Status.ACTIVE && toAccount.getStatus()==Status.ACTIVE) {
            	if (fromAccount.getBalance() >= amount) {
                    fromAccount.setBalance(fromAccount.getBalance() - amount);
                    toAccount.setBalance(toAccount.getBalance() + amount);
                    bankAccountRepository.save(fromAccount);
                    bankAccountRepository.save(toAccount);
                    Transfer transfer=new Transfer();
                    transfer.setAmount(amount);
                    transfer.setFromAccount(fromAccount);
                    transfer.setToAccount(toAccount);
                    transfer.setTimeStamp(LocalDateTime.now());
                    transferRepository.save(transfer);
                    
                    
            }
            	else {
                    throw new InsufficientBalanceException("Insufficient balance in the source account.");
                }
		}
            else {
                throw new AccountDeactiveException("Both accounts must be active to perform the transfer.");
            }
		
	}
		else {
            throw new UserNotFoundException("One or both accounts do not exist.");
        }

}

	public List<Transfer> getTransferToUser(User user) {
		// TODO Auto-generated method stub
		return transferRepository.findByToAccount_User(user);
	}
	public List<Transfer> getTransferFromUser(User user) {
		// TODO Auto-generated method stub
		return transferRepository.findByFromAccount_User(user);
	}
	public List<Transfer>getAllTransfers(User user){
		List<Transfer>sentTransfers=transferRepository.findByFromAccount_User(user);
		List<Transfer>receivedTransfers=transferRepository.findByToAccount_User(user);
		sentTransfers.addAll(receivedTransfers);
		return sentTransfers;
	}

	public void transferFromAccounntToAccount(Long senderAccountId, Long receiverAccountId, double amount) throws InsufficientBalanceException  {
		// TODO Auto-generated method stub
		BankAccount senderAccount = bankAccountRepository.findById(senderAccountId)
                .orElseThrow(() -> new AccountNotFoundException ("Sender account not found"));
        BankAccount receiverAccount = bankAccountRepository.findById(receiverAccountId)
                .orElseThrow(() -> new AccountNotFoundException ("Receiver account not found"));
		
			
        if (senderAccount.getBalance() < amount) {
            throw new InsufficientBalanceException("Insufficient balance for the transfer");
        }

        if(senderAccount.getStatus()!=Status.ACTIVE || receiverAccount.getStatus()!=Status.ACTIVE ) {
        	throw new AccountDeactiveException("Both account should be active");
        	
        }
        else {
        	 senderAccount.setBalance(senderAccount.getBalance()-amount);
             receiverAccount.setBalance(receiverAccount.getBalance()+amount);
             

             bankAccountRepository.save(senderAccount);
             bankAccountRepository.save(receiverAccount);
             Transfer transfer=new Transfer();
             transfer.setAmount(amount);
             transfer.setFromAccount(senderAccount);
             transfer.setToAccount(receiverAccount);
             transfer.setTimeStamp(LocalDateTime.now());
             transferRepository.save(transfer);
        	
        }
       
		
		
	}
	
}
