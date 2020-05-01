# Spring batch

Spring batch is majorly used to solve the busniess problem with large data base. 
On below some parameters we decide to take spring batch as a option.

1. Large data to process
2. Parallel processing of a jobs
3. To handle complete buniess logics fail-over mechanism. 


ref :  https://docs.spring.io/spring-batch/docs/current/reference/html/spring-batch-intro.html#spring-batch-intro


## Busniess Problem 
We need to send email all the new transactions on some web portal. Generate a file [csv] with all the email-id's.


Below is the step by step documentation for a batch implementation.
## Step 1 : configure database 
For keeping all the jobs and step's execution we need to conifure database.

```bash
@Configuration
@PropertySource("classpath:/batch-psql.properties")
public class DataSourceConfiguration {

	@Autowired
	private Environment environment;

	@Autowired
	private ResourceLoader resourceLoader;

	@PostConstruct
	protected void initialize() {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(resourceLoader.getResource(environment.getProperty("batch.schema.script")));
		populator.setContinueOnError(true);
		DatabasePopulatorUtils.execute(populator, dataSource());
	}

	@Bean 
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(environment.getProperty("batch.jdbc.driver"));
		dataSource.setUrl(environment.getProperty("batch.jdbc.url"));
		dataSource.setUsername(environment.getProperty("batch.jdbc.user"));
		dataSource.setPassword(environment.getProperty("batch.jdbc.password"));
		return dataSource;
	}
	
	 
}


```

Update the batch config file : batch-psql.properties

```bash
batch.drop.script=classpath:/org/springframework/batch/core/schema-drop-postgresql.sql
batch.schema.script=classpath:/org/springframework/batch/core/schema-postgresql.sql


batch.jdbc.driver=org.postgresql.Driver
batch.jdbc.url=jdbc:postgresql://localhost:5432/notification
batch.jdbc.user=postgres
batch.jdbc.password=root
 
```

## Step 2 : Enable bach processing with @EnableBatchProcessing and Encapsulte job and step in it. 

### 2.1 Enable Batch processing:

```bash
/**
 * 
 * @author hem
 *
 */
@Configuration
@EnableBatchProcessing
public class BatchConfig {
.
.
.

```

### 2.2 Write down your job.

```bash
/**
	 * informUser is the job which intent to send email to the email id's captured in with transactions.
	 * @return
	 */
@Bean
public Job informUser() {
	return this.jobBuilderFactory.get("informUser").start(fetchTransaction()).build();
}
```
 
 ### 2.2 Add steps in you job.
 A job can have multiple steps. I am here adding just on step name fetchTransaction().
 Basically A Job does three things.
 	1. Reads a large records from a database/file/queue.
	2. Processes data
	3. Writes back data in a modified form.

```bash
/**
* Step
* 
* @return
*/
private Step fetchTransaction() {
		return stepBuilderFactory.get("fetchTransaction").<TransactionEntity, 		TransactionEntity>chunk(10).reader(reader)
				.processor(processor).writer(writer()).build();

}
```

 ### 2.3 ADD :  Reader, Writer and processor to complete the step.

Spring batch provide ItemReader, ItemWriter & ItemProcessor. we can either implement our own reader/writer/processor or can use provided by the spring batch framwork.

#### Reader :   

```bash
/**
 * 
 * @author hem
 *
 */
@Component
public class TransactionReader implements ItemReader<TransactionEntity> {

	@Override
	public TransactionEntity read()
			throws Exception {
		....
	}

}

```
#### Processor :  

```bash
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

```
#### Writer : 

```bash
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
						setNames(new String[] { "id", "transactionId", "mobileNumber", "emailId", 								"amount",
								"mailTriggered" });
					}
				});
			}
		});
		return writer;
	}

```

 ## Step 3 : Disable default batch jobs and create a controller for starting it.
```bash
spring.batch.job.enabled=false	
```

Contoller
```bash
	@RestController
public class JobController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job job;

	@GetMapping("/api/batch/email")
	public void runEmailJob() {
		try {
			JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
					.toJobParameters();
			jobLauncher.run(job, jobParameters);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}

	}

}
```


