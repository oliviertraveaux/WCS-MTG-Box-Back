package com.wcs.mtgbox;

import com.wcs.mtgbox.files.application.StorageProperties;
import com.wcs.mtgbox.files.domain.service.IStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties(StorageProperties.class)
public class MtgboxApplication {

	public static void main(String[] args) {
		SpringApplication.run(MtgboxApplication.class, args);
	}

	@Bean
	CommandLineRunner init(IStorageService storageService) {
		return (args) -> {
//			storageService.deleteAll();
			storageService.init();
		};
	}
}
