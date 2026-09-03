package com.salest.freya;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // Enables automatic timestamp tracking
public class FreyaApplication {

	public static void main(String[] args) {
		SpringApplication.run(FreyaApplication.class, args);
	}

}
