package com.distributed_rate_limiting;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RateLimitingFilter extends OncePerRequestFilter {
	
	@Autowired
    private RateLimitingService rateLimitingService;
	
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String clientId = request.getRemoteAddr(); // Use IP address as client ID
        String apiEndpoint = request.getRequestURI(); // Use URI as API key
        
        if (!rateLimitingService.isRequestAllowed(apiEndpoint, clientId)) {
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            response.getWriter().write("Too Many Requests");
            return;
        }

        filterChain.doFilter(request, response);
    }

}
