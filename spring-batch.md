# Spring batch

Spring batch is majorly used to solve the busniess problem with large data base. 
On below some parameters we decide to take spring batch as a option.

### 1. Large data to process  
### 1. Parallel processing of a jobs
### 1. To handle complete buniess logics fail-over mechanism. 

ref :  https://docs.spring.io/spring-batch/docs/current/reference/html/spring-batch-intro.html#spring-batch-intro


## Busniess Problem : We need to send email all the new registrations on some web portal. 

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


### 1.2 Enable config server.
```bash
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-config-server</artifactId>
</dependency>


```


### 1.3 add properties in application/bootstrap file in the config server application.
```bash
server.port=8888
spring.cloud.config.server.git.uri=https://github.com/versatile-hem/spring-concepts-poc/
spring.cloud.config.server.git.skipSslValidation=true
spring.cloud.config.server.git.search-paths=config-files  //Used in case you file is kept under some directory in git repo.
```

### 1.4 Start your application and verify.
Once config server is up check with below url.

<host>:<port>/{application-name}/{environment}
Eg :  http://localhost:8888/poc-1/uat



## Step 2 : Make your application as a client of config server : 

### 2.1 Import dependency for config server :

```bash

<dependency>
<groupId>org.springframework.cloud</groupId>
<artifactId>spring-cloud-starter-config</artifactId>
</dependency>
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-actuator</artifactId>
</dependency>

```

### 2.2 add properties in application file in the config client application.

```bash
spring.cloud.config.uri=http://localhost:8888
spring.profiles.active=uat
spring.application.name=poc-1

management.endpoints.web.exposure.include=refresh

endpoints.actuator.enabled=true
```

Enable refresh scope, 



Verify refresh API 
POST http://localhost:8080/actuator/refresh

