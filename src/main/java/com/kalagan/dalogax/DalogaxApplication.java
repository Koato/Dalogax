package com.kalagan.dalogax;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DalogaxApplication {

	public static void main(String[] args) {
		SpringApplication.run(DalogaxApplication.class, args);
	}

}
