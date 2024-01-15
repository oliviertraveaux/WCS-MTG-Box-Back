package com.wcs.mtgbox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MtgboxApplication {

	public static void main(String[] args) {
		SpringApplication.run(MtgboxApplication.class, args);
	}

}
