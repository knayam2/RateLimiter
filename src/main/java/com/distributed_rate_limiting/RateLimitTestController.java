package com.distributed_rate_limiting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateLimitTestController {
	
	 @Autowired
	    private RateLimitTestExecutor rateLimitTestExecutor;

	    @GetMapping("/test-rate-limits")
	    public String testRateLimits() throws InterruptedException {
	        rateLimitTestExecutor.executeTest();
	        return "Rate limit test completed. Check the logs for results.";
	    }

}
