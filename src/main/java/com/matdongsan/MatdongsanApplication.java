package com.matdongsan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MatdongsanApplication {

	public static void main(String[] args) {
		SpringApplication.run(MatdongsanApplication.class, args);
	}

}
