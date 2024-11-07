package com.distributed_rate_limiting;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RateLimitingService {

	@Autowired
	private RateLimitConfigRepository rateLimitConfigRepository;

	@Autowired
	private RateLimitTrackerRepository rateLimitTrackerRepository;

	@Transactional
	public boolean isRequestAllowed(String apiEndpoint, String clientId) {

		Optional<RateLimitConfig> configOptional = rateLimitConfigRepository.findById(apiEndpoint);
		if (!configOptional.isPresent()) {
			return true; // No rate limit set, allow the request
		}

		RateLimitConfig config = configOptional.get();
		String key = apiEndpoint + ":" + clientId;
		long currentTime = System.currentTimeMillis() / 1000; // Current time in seconds

		// Find existing tracker or create a new one
		RateLimitTracker tracker = rateLimitTrackerRepository.findById(key)
				.orElse(new RateLimitTracker(key, currentTime, 0));

		try {

			if (currentTime > tracker.getWindowStart() + config.getWindow()) {
				// Reset count and window start
				tracker.setWindowStart(currentTime);
				tracker.setRequestCount(1);
			} else {
				if (tracker.getRequestCount() < config.getLimit()) {
					tracker.setRequestCount(tracker.getRequestCount() + 1);
				} else {
					return false; // Rate limit exceeded
				}
			}

			// Save the updated tracker
			rateLimitTrackerRepository.save(tracker);
			return true; // Request allowed
		} catch (OptimisticLockingFailureException e) {
			// Handle optimistic locking conflict
			// If an optimistic locking failure occurs, it means the document was modified
			// concurrently.
			// You can decide whether to retry, fail the request, or any other appropriate
			// action.
			return false;
		}
	}
}
