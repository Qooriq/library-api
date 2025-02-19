package com.java.akdev.booktrackerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BookTrackerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookTrackerServiceApplication.class, args);
	}

}
