package com.kaan.jakarta.contact;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ContactService {

    @PersistenceContext(unitName = "contactPU")
    private EntityManager entityManager;

    @Transactional
    public Contact createContact(Contact contact) {
        entityManager.persist(contact);
        return contact;
    }

    public Contact getContact(int id) {
        return entityManager.find(Contact.class, id);
    }

    public List<Contact> getAllContacts() {
        return entityManager.createQuery("SELECT c FROM Contact c", Contact.class)
                .getResultList();
    }

    @Transactional
    public Contact updateContact(Contact contact) {
        return entityManager.merge(contact);
    }

    @Transactional
    public void deleteContact(int id) {
        Contact contact = getContact(id);
        if (contact != null) {
            entityManager.remove(contact);
        }
    }
}