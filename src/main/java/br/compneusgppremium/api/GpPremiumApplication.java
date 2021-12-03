package br.compneusgppremium.api;

import br.compneusgppremium.api.service.FilesStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GpPremiumApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
		return builder.sources(GpPremiumApplication.class);
	}

	public static void main(String[] args) {

		SpringApplication.run(GpPremiumApplication.class, args);
	}

	@Bean
	CommandLineRunner init(FilesStorageService filesStorageService) {
		return (args) -> {
			filesStorageService.init();
		};
	}
}
