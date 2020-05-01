package com.spring.poc.springbatchpoc.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * 
 * @author Hem
 *
 */
@Entity
@Table(name = "transaction")
public class TransactionEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_seq")
	private Long id;

	@Column(name = "transaction_id")
	private String transactionId;

	@Column(name = "mobile_number")
	private String mobileNumber;

	@Column(name = "email_id")
	private String emailId;

	@JsonSerialize(using = ToStringSerializer.class)
	@Column(name = "amount")
	private Double amount; 
	

	@Column(name = "mail_triggered")
	private int mailTriggered = 0;
	
	public TransactionEntity() {
		
	}

	public TransactionEntity(String transactionId, String mobileNumber, String emailId, Double amount) {
		super();
		this.transactionId = transactionId;
		this.mobileNumber = mobileNumber;
		this.emailId = emailId;
		this.amount = amount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public int getMailTriggered() {
		return mailTriggered;
	}

	public void setMailTriggered(int mailTriggered) {
		this.mailTriggered = mailTriggered;
	}
 
	
	

}