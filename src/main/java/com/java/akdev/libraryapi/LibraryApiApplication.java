package com.java.akdev.libraryapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication()
@EnableFeignClients(basePackages = "com.java.akdev.libraryapi.feignclient")
public class LibraryApiApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(LibraryApiApplication.class, args);
    }

}
