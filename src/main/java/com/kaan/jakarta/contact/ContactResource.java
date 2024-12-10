package com.kaan.jakarta.contact;

import com.kaan.jakarta.auth.Secured;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Path("contact")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Secured
public class ContactResource {
    private static final Logger logger = LoggerFactory.getLogger(ContactResource.class);

    @Inject
    private ContactService contactService;

    @GET
    public Response getContacts() {
        logger.info("Fetching all contacts");
        List<Contact> contacts = contactService.getAllContacts();
        logger.info("Fetched {} contacts", contacts.size());
        return Response.ok(contacts).build();
    }

    @GET
    @Path("{id}")
    public Response getContact(@PathParam("id") int id) {
        logger.info("Fetching contact with ID: {}", id);
        Contact contact = contactService.getContact(id);
        if (contact != null) {
            logger.info("Contact found: {}", contact);
            return Response.ok(contact).build();
        }
        logger.warn("Contact with ID {} not found", id);
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response createContact(Contact contact) {
        logger.info("Creating new contact: {}", contact);
        Contact created = contactService.createContact(contact);
        logger.info("Contact created successfully: {}", created);
        return Response.status(Response.Status.CREATED)
                .entity(created)
                .build();
    }

    @PUT
    @Path("{id}")
    public Response updateContact(@PathParam("id") int id, Contact contact) {
        logger.info("Updating contact with ID: {}", id);
        contact.setId(id);
        Contact updated = contactService.updateContact(contact);
        logger.info("Contact updated successfully: {}", updated);
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteContact(@PathParam("id") int id) {
        logger.info("Deleting contact with ID: {}", id);
        contactService.deleteContact(id);
        logger.info("Contact with ID {} deleted successfully", id);
        return Response.noContent().build();
    }
}