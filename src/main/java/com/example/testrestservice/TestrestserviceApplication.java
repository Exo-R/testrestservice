package com.example.testrestservice;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@Log4j2
@SpringBootApplication
public class TestrestserviceApplication {

	public static void main(String[] args) {
		log.info("test");
		SpringApplication.run(TestrestserviceApplication.class, args);
	}

}
