# Spring cloud config  

Spring cloud gives us feature to change properties of and application on the fly. It helps our application to run without downtime and easy to handle for operations.



## Step 1 : configure application config server : 

### 1.1 Import dependency for config server :

```bash
@SpringBootApplication
@EnableConfigServer
public class SpringConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringConfigServerApplication.class, args);
	}

}



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

