package com.distributed_rate_limiting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DistributedRateLimitingApplication {

	public static void main(String[] args) {
		SpringApplication.run(DistributedRateLimitingApplication.class, args);
	}

}
