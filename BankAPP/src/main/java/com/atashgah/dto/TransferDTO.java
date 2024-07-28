package com.atashgah.dto;

import java.time.LocalDateTime;
import java.util.Date;

import com.atashgah.model.BankAccount;

public class TransferDTO {
	private Long fromAccount;
	private Long toAccount;
	private double amount;
	private LocalDateTime time;
	public Long getFromAccount() {
		return fromAccount;
	}
	public void setFromAccount(Long fromAccount) {
		this.fromAccount = fromAccount;
	}
	public Long getToAccount() {
		return toAccount;
	}
	public void setToAccount(Long toAccount) {
		this.toAccount = toAccount;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	
	

}
