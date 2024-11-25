package com.kaan.jakarta.contact;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("contact")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ContactResource {

    @Inject
    private ContactService contactService;

    @GET
    public Response getContacts() {
        List<Contact> contacts = contactService.getAllContacts();
        return Response.ok(contacts).build();
    }

    @GET
    @Path("{id}")
    public Response getContact(@PathParam("id") int id) {
        Contact contact = contactService.getContact(id);
        if (contact != null) {
            return Response.ok(contact).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response createContact(Contact contact) {
        Contact created = contactService.createContact(contact);
        return Response.status(Response.Status.CREATED)
                .entity(created)
                .build();
    }

    @PUT
    @Path("{id}")
    public Response updateContact(@PathParam("id") int id, Contact contact) {
        contact.setId(id);
        Contact updated = contactService.updateContact(contact);
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteContact(@PathParam("id") int id) {
        contactService.deleteContact(id);
        return Response.noContent().build();
    }
}