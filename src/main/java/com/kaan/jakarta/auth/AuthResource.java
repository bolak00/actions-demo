// src/main/java/com/kaan/jakarta/auth/AuthResource.java
package com.kaan.jakarta.auth;

import com.kaan.jakarta.auth.dto.LoginRequest;
import com.kaan.jakarta.auth.dto.LoginResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {
    private static final Logger logger = LoggerFactory.getLogger(AuthResource.class);

    @Inject
    private AuthService authService;

    @POST
    @Path("login")
    public Response login(LoginRequest loginRequest) {
        logger.info("Received login request for username: {}", loginRequest.getUsername());
        String token = authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());

        if (token != null) {
            logger.info("Login successful for username: {}", loginRequest.getUsername());
            LoginResponse response = LoginResponse.builder()
                    .token(token)
                    .username(loginRequest.getUsername())
                    .build();
            return Response.ok(response).build();
        }
        logger.warn("Login failed for username: {}", loginRequest.getUsername());
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @POST
    @Path("register")
    public Response register(LoginRequest request) {
        logger.info("Received registration request for username: {}", request.getUsername());
        User user = authService.createUser(request.getUsername(), request.getPassword(), Role.USER);
        logger.info("User registered successfully: {}", user.getUsername());
        return Response.status(Response.Status.CREATED)
                .entity(user)
                .build();
    }

    @POST
    @Path("register/admin")
    @AdminSecured
    public Response registerAdmin(LoginRequest request) {
        logger.info("Received admin registration request for username: {}", request.getUsername());
        User user = authService.createUser(request.getUsername(), request.getPassword(), Role.ADMIN);
        logger.info("Admin user registered successfully: {}", user.getUsername());
        return Response.status(Response.Status.CREATED)
                .entity(user)
                .build();
    }
}