package com.example.payment_services.security;

import com.example.payment_services.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
@Component
public class AuthUtils {
    @Value("${jwt.securityKey}")
    private String securityKey;

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(securityKey.getBytes());
    }

    public String generateJwtToken(User user){
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("userId", user.getId().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
