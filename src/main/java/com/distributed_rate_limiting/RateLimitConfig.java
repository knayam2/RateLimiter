package com.distributed_rate_limiting;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "rate_limits_config")
public class RateLimitConfig {
	
	@Id
    private String apiEndpoint; // e.g., "/api/test"
    private int limit; // e.g., 100 requests
    private int window; // e.g., 60 seconds
 
	
	public RateLimitConfig(String apiEndpoint, int limit, int window) {
		super();
		this.apiEndpoint = apiEndpoint;
		this.limit = limit;
		this.window = window;
	}

	
    // Getters and Setters
    public String getApiEndpoint() {
        return apiEndpoint;
    }

    public void setApiEndpoint(String apiEndpoint) {
        this.apiEndpoint = apiEndpoint;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getWindow() {
        return window;
    }

    public void setWindow(int window) {
        this.window = window;
    }
}
