
Spring cloud config:  
improve live-liness of the running application. This helps our application to change property files on the fly as well as it maintain the log record of change in property file.







Step 1 : configure application config server : 

1.1 Import dependency for config server :



<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-config-server</artifactId>
</dependency>




1.2 Enable config server.

1.3 add properties in application/bootstrap file in the config server application.

server.port=8888
spring.cloud.config.server.git.uri=https://github.com/versatile-hem/spring-concepts-poc/
spring.cloud.config.server.git.skipSslValidation=true
spring.cloud.config.server.git.search-paths=config-files  //Used in case you file is kept under some directory in git repo.

1.3 Start your application and verify.
Once config server is up check with below url.

<host>:<port>/{application-name}/{environment}
Eg :  http://localhost:8888/poc-1/uat



Step 2 : Make your application as a client of config server : 

2.1 Import dependency for config server :



<dependency>
<groupId>org.springframework.cloud</groupId>
<artifactId>spring-cloud-starter-config</artifactId>
</dependency>
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-actuator</artifactId>
</dependency>



2.2 add properties in application file in the config client application.

spring.cloud.config.uri=http://localhost:8888
spring.profiles.active=uat
spring.application.name=poc-1

management.endpoints.web.exposure.include=refresh

endpoints.actuator.enabled=true

Enable refresh scope, 



Verify refresh API 
POST http://localhost:8080/actuator/refresh

