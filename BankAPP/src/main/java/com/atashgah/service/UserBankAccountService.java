package com.atashgah.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atashgah.dto.BankDTO;
import com.atashgah.dto.BankMapper;
import com.atashgah.exception.AccountAlreadyActiveException;
import com.atashgah.exception.AccountNotFoundException;
import com.atashgah.model.BankAccount;
import com.atashgah.model.BankAccount.Status;
import com.atashgah.model.User;
import com.atashgah.repository.BankAccountRepository;

@Service
public class UserBankAccountService {
	
	@Autowired
	private BankAccountRepository bankAccountRepository;

	public List<BankDTO> getUserBankAccounts(User user) {
        List<BankAccount>bankAccount = bankAccountRepository.findByUser(user);
        return BankMapper.toBankDTOList(bankAccount);
    }
	public Optional<BankDTO> getUserSpecificBankAccount(User user, Long accountId) throws Exception {
		bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException ("Account not found for any user"));
		BankAccount account = bankAccountRepository.findByUserAndId(user,accountId)
                .orElseThrow(() -> new AccountNotFoundException ("Account not found for specific user "+user.getPin()));
		
        return Optional.ofNullable(BankMapper.toBankDTO(account));
    }
	public BankDTO addBankAccount(User user, BankDTO bankDTO) {
		// TODO Auto-generated method stub
		BankAccount bankAccount=new BankAccount();
		bankAccount.setUser(user);
		bankAccountRepository.save(bankAccount);
		return BankMapper.toBankDTO(bankAccount);
	}
	public void deleteUserBankAccount(User user, Long id) {
		
		BankAccount bankAccountOptional = bankAccountRepository.findByUserAndId(user, id)
				.orElseThrow(()->new AccountNotFoundException("Account not found"));
		bankAccountRepository.deleteById(id);
        
		
	}
	public BankDTO setUserBankAccountActive(User user, Long id) throws Exception {
		// TODO Auto-generated method stub
		BankAccount bankAccount=bankAccountRepository.findByUserAndId(user, id)
				.orElseThrow(()->new AccountNotFoundException ("Account not found for specific user "+user.getPin()));
		
		if(bankAccount.getStatus().equals(BankAccount.Status.ACTIVE)) {
			throw new AccountAlreadyActiveException("Account is already active");
		}
		
		bankAccount.setStatus(BankAccount.Status.ACTIVE);
		bankAccountRepository.save(bankAccount);
		return BankMapper.toBankDTO(bankAccount);
		
		
	}
	public Optional<BankAccount>getBankAccount(Long id){
		return bankAccountRepository.findById(id);
	}
	public List<BankDTO> getUserActiveBankAccounts(User user) {
		// TODO Auto-generated method stub
		
		List<BankAccount>bankAccount= bankAccountRepository.findByUserAndStatus(user, Status.ACTIVE);
		return BankMapper.toBankDTOList(bankAccount);
	}
	
	

}
