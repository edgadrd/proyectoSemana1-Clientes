package com.nttdata.customerms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class CustomerMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerMsApplication.class, args);
	}

}
