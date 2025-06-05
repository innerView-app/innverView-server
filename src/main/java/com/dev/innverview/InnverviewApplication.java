package com.dev.innverview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class InnverviewApplication {

	public static void main(String[] args) {
		SpringApplication.run(InnverviewApplication.class, args);
	}

}
