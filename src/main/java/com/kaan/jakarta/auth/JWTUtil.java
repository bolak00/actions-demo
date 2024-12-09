package com.kaan.jakarta.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.enterprise.context.ApplicationScoped;

import javax.crypto.SecretKey;
import java.util.Date;

@ApplicationScoped
public class JWTUtil {
    private static final long EXPIRATION_TIME = 864_000_000; // 10 days
    private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(String username, Role role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role.name())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Role getRoleFromToken(String token) {
        String role = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
        return Role.valueOf(role);
    }
}