package com.example.practicelab;

import com.example.practicelab.Repositories.CustomerRepository;
import com.example.practicelab.entities.Customer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class PracticeLabApplication {

	public static void main(String[] args) {
		SpringApplication.run(PracticeLabApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(CustomerRepository customerRepository) {
		return args -> {
			customerRepository.save(new Customer(null, 115, "Jasper Diaz", 15000.0, 5, "Savings-Deluxe", new Date()));
			customerRepository.save(new Customer(null, 112, "Zanip Mendez", 5000.0, 2, "Savings-Deluxe", new Date()));
			customerRepository.save(new Customer(null, 113, "Geronima Esper", 6000.0, 5, "Savings-Regular", new Date()));
		};
	}
}
