package com.distributed_rate_limiting;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateLimitTrackerRepository extends MongoRepository<RateLimitTracker, String>  {

}
