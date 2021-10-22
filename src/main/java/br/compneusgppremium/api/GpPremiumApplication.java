package br.compneusgppremium.api;

import br.compneusgppremium.api.service.FilesStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;


@SpringBootApplication
public class GpPremiumApplication {

	public static void main(String[] args) {
		SpringApplication.run(GpPremiumApplication.class, args);
	}

	@Bean
	CommandLineRunner init(FilesStorageService filesStorageService) {
		return (args) -> {
			filesStorageService.deleteAll();
			filesStorageService.init();
		};
	}
}
