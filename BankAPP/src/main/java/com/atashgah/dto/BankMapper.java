package com.atashgah.dto;

import java.util.ArrayList;
import java.util.List;

import com.atashgah.model.BankAccount;

public class BankMapper {
	public static BankAccount toEntity(BankDTO bankDTO) {
		BankAccount bankAccount=new BankAccount();
		bankAccount.setBalance(bankDTO.getBalance());
		bankAccount.setId(bankDTO.getId());
		bankAccount.setStatus(bankAccount.getStatus());
		
		return bankAccount;
	}
	
	public static BankDTO toBankDTO(BankAccount bankAccount) {
		BankDTO bankDTO=new BankDTO();
		bankDTO.setBalance(bankAccount.getBalance());
		bankDTO.setId(bankAccount.getId());
		bankDTO.setStatus(bankAccount.getStatus());
		return bankDTO;
	}
	public static List<BankDTO>toBankDTOList(List<BankAccount>bankAccount){
		List<BankDTO>result=new ArrayList<>();
		
		for(int i=0;i<bankAccount.size();i++) {
			BankDTO b=new BankDTO();
			b.setBalance(bankAccount.get(i).getBalance());
			b.setId(bankAccount.get(i).getId());
			b.setStatus(bankAccount.get(i).getStatus());
			result.add(b);
		}
		
		
		return result;
	}
	

}
