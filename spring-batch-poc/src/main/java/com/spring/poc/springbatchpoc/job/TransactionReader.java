package com.spring.poc.springbatchpoc.job;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spring.poc.springbatchpoc.dao.TransactionEntity;
import com.spring.poc.springbatchpoc.dao.TransactionRepository;

/**
 * 
 * @author hem
 *
 */
@Component
public class TransactionReader implements ItemReader<TransactionEntity> {

	@Autowired
	private TransactionRepository repository;

	private List<TransactionEntity> transactions;

	private int index;

	@PostConstruct
	public void init() {
		transactions = repository.getAll();
		index = 0;
	}

	@Override
	public TransactionEntity read()
			throws Exception {
		TransactionEntity entity = null;
		if (transactions != null && transactions.size() > index) {
			entity = transactions.get(index);
			index++;
		}
		return entity;
	}

}
