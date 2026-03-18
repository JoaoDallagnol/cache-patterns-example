package com.patterns.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CacheExamplesApplication {

	static void main(String[] args) {
		SpringApplication.run(CacheExamplesApplication.class, args);
	}

}
