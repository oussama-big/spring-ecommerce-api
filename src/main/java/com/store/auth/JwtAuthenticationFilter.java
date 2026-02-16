package com.codewithmosh.store.auth;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // This filter will intercept incoming requests and validate the JWT token
    // You can implement the logic to extract the token from the Authorization header,
    // validate it, and set the authentication in the security context if valid.
    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Extract the token from the Authorization header
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
           
           filterChain.doFilter(request, response); // Continue with the next filter in the chain
           return;
        }

        var jwtToken = authHeader.replace("Bearer ", ""); // Remove "Bearer " prefix if present
        // Validate the token (you can use JwtService for this)
        // If valid, set the authentication in the security context
        var jwt = jwtService.parseToken(jwtToken); // This will throw an exception if the token is invalid, which we can catch and handle accordingly
        if(jwt == null || jwt.isExpired()){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // var role = jwt.getRole(); // Assuming the role is stored as a claim in the token
        // var userId = jwt.getUserId();
        var authentication = new UsernamePasswordAuthenticationToken(
            jwt.getUserId(),
            null,
            List.of( new SimpleGrantedAuthority("ROLE_" + jwt.getRole().name()))
         );

         authentication.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication  );
        filterChain.doFilter(request, response); // Continue with the next filter in the chain





}
}
