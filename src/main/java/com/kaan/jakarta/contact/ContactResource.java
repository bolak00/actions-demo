package com.kaan.jakarta.contact;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("contact")
public class ContactResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContacts() {
        return Response.ok()
                .build();
    }
}
