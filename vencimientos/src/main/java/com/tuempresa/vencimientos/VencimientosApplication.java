package com.tuempresa.vencimientos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling

public class VencimientosApplication {
	public static void main(String[] args) {
		SpringApplication.run(VencimientosApplication.class, args);
	}

}
