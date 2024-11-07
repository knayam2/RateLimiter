package com.distributed_rate_limiting;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
	
	 private final RateLimitConfigRepository repository;

	    public DataInitializer(RateLimitConfigRepository repository) {
	        this.repository = repository;
	    }


	@Override
	public void run(String... args) throws Exception {
		
		List<RateLimitConfig> rateLimitConfigList = new ArrayList<>();
		
		 // Initialize rate limit configurations
		RateLimitConfig rateLimitConfig1 = new RateLimitConfig("/api/v1/login", 5, 60);
		RateLimitConfig rateLimitConfig2 = new RateLimitConfig("/api/v1/register", 3, 30);
		
		rateLimitConfigList.add(rateLimitConfig1);
		rateLimitConfigList.add(rateLimitConfig2);
		
        repository.saveAll(rateLimitConfigList); // 100 requests per minute
        // Add more configurations as needed
        

	}

}
