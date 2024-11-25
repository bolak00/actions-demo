package com.kaan.jakarta.contact;

import lombok.Data;

@Data
public class Contact {
    private final int id;
    private String email;
    private String firstName;
    private String surname;

}
