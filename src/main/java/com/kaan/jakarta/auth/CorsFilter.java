package com.kaan.jakarta.auth;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.HEADER_DECORATOR)
public class CorsFilter implements ContainerResponseFilter {

//    private static final String ALLOWED_ORIGIN = "https://bolatools.nl";
    private static final String ALLOWED_ORIGIN = "*";


    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        String origin = requestContext.getHeaderString("Origin");

        // Allow only requests from the specified origin
        if (ALLOWED_ORIGIN.equals(origin)) {
            responseContext.getHeaders().add("Access-Control-Allow-Origin", ALLOWED_ORIGIN);
            responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            responseContext.getHeaders().add("Access-Control-Allow-Headers", "Authorization, Content-Type");
            responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
        }
    }
}