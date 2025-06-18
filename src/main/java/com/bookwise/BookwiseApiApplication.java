package com.bookwise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "com.bookwise")
@EntityScan(basePackages = "com.bookwise.adapters.out.persistence")
public class BookwiseApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookwiseApiApplication.class, args);
	}

}
