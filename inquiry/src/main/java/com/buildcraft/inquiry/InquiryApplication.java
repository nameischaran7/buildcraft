package com.buildcraft.inquiry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class InquiryApplication {
	public static void main(String[] args) {
		SpringApplication.run(InquiryApplication.class, args);
	}

}
