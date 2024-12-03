package com.fplima.mvc.sb.departamento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class DepartamentoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DepartamentoApplication.class, args);
	}

}
