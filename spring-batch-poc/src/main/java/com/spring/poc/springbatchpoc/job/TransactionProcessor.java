package com.spring.poc.springbatchpoc.job;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spring.poc.springbatchpoc.busniess.TaskManger;
import com.spring.poc.springbatchpoc.dao.TransactionEntity;

/**
 * 
 * @author hem
 *
 */
@Component
public class TransactionProcessor implements ItemProcessor<TransactionEntity, TransactionEntity> {

	@Autowired
	private TaskManger tm;

	@Override
	public TransactionEntity process(TransactionEntity item) throws Exception {
		tm.sendMail(item.getEmailId());
		item.setMailTriggered(1);
		return tm.updateTransaction(item.getId());
	}

}
