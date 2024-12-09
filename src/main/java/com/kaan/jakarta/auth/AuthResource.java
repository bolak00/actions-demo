// src/main/java/com/kaan/jakarta/auth/AuthResource.java
package com.kaan.jakarta.auth;

import com.kaan.jakarta.auth.dto.LoginRequest;
import com.kaan.jakarta.auth.dto.LoginResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    private AuthService authService;

    @POST
    @Path("login")
    public Response login(LoginRequest loginRequest) {
        String token = authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());

        if (token != null) {
            LoginResponse response = LoginResponse.builder()
                    .token(token)
                    .username(loginRequest.getUsername())
                    .build();
            return Response.ok(response).build();
        }

        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @POST
    @Path("register")
    public Response register(LoginRequest request) {
        User user = authService.createUser(request.getUsername(), request.getPassword(), Role.USER);
        return Response.status(Response.Status.CREATED)
                .entity(user)
                .build();
    }

    @POST
    @Path("register/admin")
    @AdminSecured
    public Response registerAdmin(LoginRequest request) {
        User user = authService.createUser(request.getUsername(), request.getPassword(), Role.ADMIN);
        return Response.status(Response.Status.CREATED)
                .entity(user)
                .build();
    }
}