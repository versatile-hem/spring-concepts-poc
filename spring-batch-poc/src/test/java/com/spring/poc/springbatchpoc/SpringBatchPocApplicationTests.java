package com.spring.poc.springbatchpoc;

import javax.batch.runtime.JobExecution;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.StepScopeTestExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import junit.framework.Assert;

@ContextConfiguration
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, StepScopeTestExecutionListener.class })
@RunWith(SpringRunner.class)

//@SpringBootTest
class SpringBatchPocApplicationTests {

	// @Test
	// void contextLoads() {
	// }
	@Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

  
	
	
	@Test
	public void testJob() throws Exception {
		 
		JobExecution jobExecution = (JobExecution) jobLauncherTestUtils.launchJob(); 
		System.out.println("status : "+jobExecution.getExitStatus());

		Assert.assertEquals("COMPLETED", jobExecution.getExitStatus());
	}
	
	
	
	

}
