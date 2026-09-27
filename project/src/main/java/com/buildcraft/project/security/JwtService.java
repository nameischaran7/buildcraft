package com.buildcraft.project.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;

@Service
public class JwtService {
    private final SecretKey secretKey = Keys.hmacShaKeyFor(
            "your-secret-key-must-be-at-least-32-bytes-long!".getBytes()
    );
    public   Claims extractClaims(String token){
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public Long extractUserId(String token)
    {
        Claims claims=extractClaims(token);
        return claims.get("userId", Long.class);
    }
    public String extractRole(String token){
        Claims claims=extractClaims(token);
        return claims.get("role",String.class);
    }
}
