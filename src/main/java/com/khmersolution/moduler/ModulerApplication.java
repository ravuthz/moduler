package com.khmersolution.moduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.khmersolution.moduler")
@EntityScan(basePackages = "com.khmersolution.moduler.domain")
@EnableJpaRepositories(basePackages = "com.khmersolution.moduler.repository")
public class ModulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModulerApplication.class, args);
	}
}
