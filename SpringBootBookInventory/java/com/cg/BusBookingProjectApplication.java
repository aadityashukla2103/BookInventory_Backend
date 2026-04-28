package com.cg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BusBookingProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusBookingProjectApplication.class, args);
		
	}

}
