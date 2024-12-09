// src/main/java/com/kaan/jakarta/auth/AuthService.java
package com.kaan.jakarta.auth;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@ApplicationScoped
public class AuthService {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private JWTUtil jwtUtil;

    public String authenticate(String username, String password) {
        User user = entityManager.createQuery(
                        "SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .getResultStream()
                .findFirst()
                .orElse(null);

        if (user != null && hashPassword(password).equals(user.getPassword())) {
            return jwtUtil.generateToken(username, user.getRole());
        }
        return null;
    }

    @Transactional
    public User createUser(String username, String password, Role role) {
        User user = User.builder()
                .username(username)
                .password(hashPassword(password))
                .role(role)
                .build();
        entityManager.persist(user);
        return user;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}