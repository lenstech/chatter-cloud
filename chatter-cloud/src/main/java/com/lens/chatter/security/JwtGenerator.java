package com.lens.chatter.security;

import com.lens.chatter.enums.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class JwtGenerator {

    private static final long JWT_TOKEN_VALIDITY = 168 * 60 * 60 * 1000; // 168 hours, 7 days
    private static final long REGISTER_TOKEN_VALIDITY = 24 * 60 * 60 * 1000; // 168 hours, 7 days

    @Value("${jwt.secret}")
    private String secret;

    // Generates a token with the given user's id and current time
    public String generateToken(UUID id, Role role) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(id.toString())
                .setAudience(role.toString())
                .setIssuedAt(new Date())
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

    public String generateInviteMailToken(String mail, Role role, String title) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role",role);
        claims.put("title", title);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(mail)
                .setExpiration(new Date(System.currentTimeMillis() + REGISTER_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS256, secret).compact();

    }

}
