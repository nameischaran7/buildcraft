package com.buildcraft.user.security;
import com.buildcraft.user.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
@Service
public class JwtService {
    private final SecretKey secretKey = Keys.hmacShaKeyFor(
            "your-secret-key-must-be-at-least-32-bytes-long!".getBytes()
    );
    public String generateToken(User user) {
        return Jwts.builder()
                .claim("userId", user.getId())
                .claim("role", user.getRole()).signWith(secretKey).compact();
    }
}
