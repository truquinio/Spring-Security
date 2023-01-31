package com.truquinio.estancias;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.truquinio.estancias.repositorios")
public class EstanciasApplication {

	public static void main(String[] args) {
		SpringApplication.run(EstanciasApplication.class, args);
	}
}