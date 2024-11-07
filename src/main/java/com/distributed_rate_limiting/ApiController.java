package com.distributed_rate_limiting;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
	
	 @GetMapping("/api/v1/login")
	    public String login() {
	        return "login successful!";
	    }
	 
	 @GetMapping("/api/v1/register")
	 public String register() {
		 return "register successful!";
	 }


}
