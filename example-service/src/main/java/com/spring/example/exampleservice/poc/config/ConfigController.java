package com.spring.example.exampleservice.poc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class ConfigController {

	
	@Value("${com.hem.example.default}")
    private String defaultValue;
	
	
	/**
	 * 
	 * @return
	 */
	@GetMapping("/api/get-default-value")
	public String getDefaultValue() {
		return "Default value is : "+defaultValue;
	}
	
}