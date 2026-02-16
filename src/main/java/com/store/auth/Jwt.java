package com.codewithmosh.store.auth;

import javax.crypto.SecretKey;

import com.codewithmosh.store.users.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.util.Date;


public class Jwt {

    private final SecretKey secretKey;
    private final Claims claims;

    public Jwt( Claims claims , SecretKey secretKey) {
        this.secretKey = secretKey;
        this.claims = claims;
    }

    public boolean isExpired(){
         return claims.getExpiration().before(new Date()); // Check if the token is expired
    }

    public Long getUserId(){
        return Long.valueOf(claims.getSubject());
    }

    public Role getRole(){
        return Role.valueOf(claims.get("role", String.class));
    }


    @Override
    public String toString(){
        return Jwts.builder()
                .setClaims(claims)
                .signWith(secretKey)
                .compact();
    }




}

