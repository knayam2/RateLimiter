package com.distributed_rate_limiting;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RateLimitTestExecutor {
	
	@Autowired
    private RateLimitingService rateLimitingService;

    @Autowired
    private RateLimitConfigRepository rateLimitConfigRepository;

    // Test method to simulate concurrent requests
    public void executeTest() throws InterruptedException {
        // Fetch rate-limiting config for all APIs from MongoDB
        var rateLimitConfigs = rateLimitConfigRepository.findAll();
        
        // Simulate concurrent requests to each API
        ExecutorService executorService = Executors.newFixedThreadPool(20); // 20 concurrent threads
        CountDownLatch latch = new CountDownLatch(rateLimitConfigs.size() * 10);  // Simulating 10 requests for each API
        
        AtomicInteger successfulRequests = new AtomicInteger(0);
        AtomicInteger blockedRequests = new AtomicInteger(0);

        // Loop through each rate-limit configuration and simulate requests
        for (var config : rateLimitConfigs) {
            String apiEndpoint = config.getApiEndpoint();

            // Simulating 10 requests per API for different client IPs (simulate IPs like "192.168.1.1", "192.168.1.2", etc.)
            for (int i = 0; i < 10; i++) {
                String clientId = " 0:0:0:0:0:0:0:1";  
               
                executorService.submit(() -> {
                    try {
                        boolean allowed = rateLimitingService.isRequestAllowed(apiEndpoint, clientId);
                        if (allowed) {
                            successfulRequests.incrementAndGet();
                        } else {
                            blockedRequests.incrementAndGet();
                        }
                    } finally {
                        latch.countDown();
                    }
                });
            }
        }

        // Wait for all tasks to finish
        latch.await();

        // Output the results
        System.out.println("Successful Requests: " + successfulRequests.get());
        System.out.println("Blocked Requests: " + blockedRequests.get());
    }

}
