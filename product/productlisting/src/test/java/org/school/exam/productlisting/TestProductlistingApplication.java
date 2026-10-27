package org.school.exam.productlisting;

import org.springframework.boot.SpringApplication;

public class TestProductlistingApplication {

	public static void main(String[] args) {
		SpringApplication.from(ProductlistingApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
