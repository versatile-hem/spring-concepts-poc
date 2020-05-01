package com.spring.poc.springbatchpoc.busniess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.poc.springbatchpoc.dao.TransactionEntity;
import com.spring.poc.springbatchpoc.dao.TransactionRepository;

@Service
public class TaskManger {

	@Autowired
	private TransactionRepository repository;

	public void sendMail(String email) throws InterruptedException {
		System.out.println(email + " email sending....");
		Thread.currentThread().sleep(1000);
		System.out.println(email + " email sent");
	}

	public TransactionEntity updateTransaction(Long id) {
		TransactionEntity entity = this.repository.findById(id).get();
		entity.setMailTriggered(1);
		return entity;

	}

}
