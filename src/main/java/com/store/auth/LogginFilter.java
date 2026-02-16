package com.codewithmosh.store.auth;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class LogginFilter extends OncePerRequestFilter {
 
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Log the request details
        System.out.println("Incoming request: " + request.getMethod() + " " + request.getRequestURI());
        
        // Continue with the next filter in the chain
        filterChain.doFilter(request, response);

        System.out.println("Outgoing response: " + response.getStatus());
    }

}
