package com.patterns.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableCaching
@EnableAsync
public class CacheExamplesApplication {

	static void main(String[] args) {
		SpringApplication.run(CacheExamplesApplication.class, args);
	}

}
