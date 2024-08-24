package com.qlish.qlish_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableMongock
@SpringBootApplication
public class QlishApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(QlishApiApplication.class, args);
	}

}
