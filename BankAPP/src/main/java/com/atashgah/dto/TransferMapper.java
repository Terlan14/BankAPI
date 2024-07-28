package com.atashgah.dto;

import java.util.ArrayList;
import java.util.List;

import com.atashgah.model.Transfer;

public class TransferMapper {
	
	public static List<TransferDTO> toTransferDTO(List<Transfer> transfer) {
		List<TransferDTO>transferDTO=new ArrayList<>();
		for(int i=0;i<transfer.size();i++) {
			TransferDTO t=new TransferDTO();
			t.setAmount(transfer.get(i).getAmount());
			t.setFromAccount(transfer.get(i).getFromAccount().getId());
			t.setToAccount(transfer.get(i).getToAccount().getId());
			t.setTime(transfer.get(i).getTimeStamp());
			transferDTO.add(t);
		}
		return transferDTO;
	}

}
