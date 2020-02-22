package com.lens.chatter.security;

import com.lens.chatter.constant.Role;
import com.lens.chatter.model.entity.User;
import com.lens.chatter.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class JwtGenerator {

    @Autowired
    private UserRepository userRepository;

    private static final long JWT_TOKEN_VALIDITY = 72 * 60 * 60 * 1000; // 72 hours

    @Value("${jwt.secret}")
    private String secret;

    // Generates a token with the given user's id and current time
    public String generateToken(UUID id, Role role) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(id.toString())
                .setAudience(role.toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public String generateMailConfirmationToken(UUID id) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(id.toString())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS256, secret).compact();

    }

}
