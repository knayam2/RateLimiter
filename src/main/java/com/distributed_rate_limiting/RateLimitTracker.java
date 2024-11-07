package com.distributed_rate_limiting;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Version;


@Document(collection = "rate_limits_tracker")
public class RateLimitTracker {
	
	    @Id
	    private String id; // Unique ID for tracking (e.g., apiEndpoint + ":" + clientId)
	    private long windowStart;
	    private int requestCount;
	    @Version  // Spring Data MongoDB handles this version field automatically for optimistic locking
	    private int version;

	    public RateLimitTracker(String id, long windowStart, int requestCount) {
	        this.id = id;
	        this.windowStart = windowStart;
	        this.requestCount = requestCount;
	        this.version = 0;
	    }

	    // Getters and Setters
	    public String getId() {
	        return id;
	    }

	    public void setId(String id) {
	        this.id = id;
	    }

	    public long getWindowStart() {
	        return windowStart;
	    }

	    public void setWindowStart(long windowStart) {
	        this.windowStart = windowStart;
	    }

	    public int getRequestCount() {
	        return requestCount;
	    }

	    public void setRequestCount(int requestCount) {
	        this.requestCount = requestCount;
	    }
	    
	    public int getVersion() {
	        return version;
	    }

	    public void setVersion(int version) {
	        this.version = version;
	    }
}
