package com.kaan.jakarta.auth;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
@AdminSecured
@Priority(Priorities.AUTHENTICATION)
public class AdminAuthFilter implements ContainerRequestFilter {

    @Inject
    private JWTUtil jwtUtil;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }

        String token = authHeader.substring("Bearer ".length()).trim();

        if (!jwtUtil.validateToken(token)) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }

        Role role = jwtUtil.getRoleFromToken(token);
        if (role != Role.ADMIN) {
            requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
        }
    }
}