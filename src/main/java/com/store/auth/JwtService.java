package com.codewithmosh.store.auth;

import java.util.Date;

import com.codewithmosh.store.users.Role;
import com.codewithmosh.store.users.User;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@Service
public class JwtService {
    // This service will be responsible for generating and validating JWT tokens
    // You can use libraries like jjwt or java-jwt to implement this functionality
    // @Value("${Spring.Jwt.secret}")
    // private String secretKey ;
    // @Value("${Spring.Jwt.expiration}")
    // private long expirationTime ; // 5 minutes in seconds
    // @Value("${Spring.Jwt.refreshExpiration}")
    // private long  refreshExpiration ; // 7 days in milliseconds

    private final JwtConfig jwtConfig;

    public JwtService(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    


    public Jwt generateToken(User user , long Time) {
        // Implement token generation logic here
       var claims = Jwts.claims()
                .subject(user.getId().toString())
                .add("email", user.getEmail())
                .add("name", user.getName())
                .add("role", user.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + Time)) // Token valid for specified time
                .build();

        return new Jwt(claims , jwtConfig.getSecretKey());
    
       
    }


    public Jwt generateAccessToken(User user) {
        return generateToken(user , jwtConfig.getExpiration() * 1000); // Convert seconds to milliseconds
    }
    public Jwt generateRefreshToken(User user ){
        return generateToken(user , jwtConfig.getRefreshExpiration() * 1000);

    }


    public Jwt parseToken(String token ){
        try{
            var claims = getClaims(token);
            return new Jwt( claims , jwtConfig.getSecretKey());
        }catch(JwtException e ){
            return null; // Invalid token
        }
    }
    

    private Claims getClaims(String token) throws IllegalArgumentException, JwtException {
        return   Jwts.parser()
                .verifyWith(jwtConfig.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
       
    }

}
