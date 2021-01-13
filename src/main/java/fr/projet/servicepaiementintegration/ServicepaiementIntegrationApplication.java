package fr.projet.servicepaiementintegration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ServicepaiementIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicepaiementIntegrationApplication.class, args);
	}
	
	@Bean
	public RestTemplate createTemplate() {
		return new RestTemplate();
	}

}
