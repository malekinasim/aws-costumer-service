package com.nasim.costumer_service;

import org.springframework.boot.SpringApplication;

public class TestCostumerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(CostumerServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
