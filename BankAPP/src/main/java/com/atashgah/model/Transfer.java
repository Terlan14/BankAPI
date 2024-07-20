package com.atashgah.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Transfer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private double amount;
	
	@ManyToOne
	@JoinColumn(name = "from_account_id", nullable = false)
	private BankAccount fromAccount;
	
	
	@ManyToOne
	@JoinColumn(name = "to_account_id", nullable = false)
	private BankAccount toAccount;
	
	private LocalDateTime timeStamp;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public BankAccount getFromAccount() {
		return fromAccount;
	}

	public void setFromAccount(BankAccount fromAccount) {
		this.fromAccount = fromAccount;
	}

	public BankAccount getToAccount() {
		return toAccount;
	}

	public void setToAccount(BankAccount toAccount) {
		this.toAccount = toAccount;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	

}
