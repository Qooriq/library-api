package com.java.akdev.bookstorageservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BookStorageServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookStorageServiceApplication.class, args);
	}

}
