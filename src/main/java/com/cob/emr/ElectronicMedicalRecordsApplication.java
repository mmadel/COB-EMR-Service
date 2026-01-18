package com.cob.emr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ElectronicMedicalRecordsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElectronicMedicalRecordsApplication.class, args);
	}

}
