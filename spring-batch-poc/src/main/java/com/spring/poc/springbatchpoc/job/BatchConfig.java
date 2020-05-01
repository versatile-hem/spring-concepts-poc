package com.spring.poc.springbatchpoc.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.spring.poc.springbatchpoc.dao.TransactionEntity;

/**
 * 
 * @author hem
 *
 */
@Configuration
@EnableBatchProcessing
public class BatchConfig {

	
	/**
	 * Configure J
	 * @return
	 */
	@Bean
	public Job dailyReportingJob() {
		return this.jobBuilderFactory.get("dailyReportingJob").start(fetchTransaction()).build();
	}

	/**
	 * Step
	 * 
	 * @return
	 */
	private Step fetchTransaction() {
		return stepBuilderFactory.get("fetchTransaction").<TransactionEntity, TransactionEntity>chunk(10).reader(reader)
				.processor(processor).writer(writer()).build();

	}

	private Resource outputResource = new FileSystemResource("/Users/hem/app/transation.csv");

	@Bean
	public FlatFileItemWriter<TransactionEntity> writer() {
		FlatFileItemWriter<TransactionEntity> writer = new FlatFileItemWriter<>();
		writer.setResource(outputResource);
		writer.setAppendAllowed(true);
		writer.setLineAggregator(new DelimitedLineAggregator<TransactionEntity>() {
			{
				setDelimiter(" | ");
				setFieldExtractor(new BeanWrapperFieldExtractor<TransactionEntity>() {
					{
						setNames(new String[] { "id", "transactionId", "mobileNumber", "emailId", "amount",
								"mailTriggered" });
					}
				});
			}
		});
		return writer;
	}

	@Autowired
	private TransactionProcessor processor;

	@Autowired
	private TransactionReader reader;

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
}
