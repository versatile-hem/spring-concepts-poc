package com.spring.poc.springbatchpoc.dao;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.batch.core.JobParametersValidator;

/**
 * 
 * @author hem
 *
 */
public class SendEmail implements Job {


	
	@Override
	public String getName() {
		return "sendEmail";
	}

	@Override
	public boolean isRestartable() {
		return true;
	}

	@Override
	public void execute(JobExecution execution) {
		System.out.println("Sending email : ");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Email sent.");
		
	}

	@Override
	public JobParametersIncrementer getJobParametersIncrementer() {
		return null;
	}

	@Override
	public JobParametersValidator getJobParametersValidator() {
		return null;
	}

}
