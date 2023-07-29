package com.blogapi;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BlogapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogapiApplication.class, args);
	}


	@Bean // when @Autowired unable to create the object so we aplly @Bean annotation on the method so that spring ioc reffered this file now spring ioc  takes the object from this and inject into reference variable of serviveImpl (modelmapper) via dependency injection
	 public ModelMapper modelMapper(){

		return new ModelMapper();
	 }
}
