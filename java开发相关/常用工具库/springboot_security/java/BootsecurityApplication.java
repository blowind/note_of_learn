package com.zxf.bootsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class BootsecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootsecurityApplication.class, args);
	}
}
