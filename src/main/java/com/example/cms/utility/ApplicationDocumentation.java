package com.example.cms.utility;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@OpenAPIDefinition
public class ApplicationDocumentation {
	@Bean
	Contact contact() {
		return new Contact().name("RamKumar")
				.url("hithub.com/Ramkumar0706");
				
	}
	
	@Bean
	Info info() {
		return new Info().title("Content-Management-System")
				.description("RestFul API with basic Register User operation")
				.version("ramkumar v1").contact(contact());
	}
	@Bean
	OpenAPI openApi() {
		return new OpenAPI().info(info());
	}
}