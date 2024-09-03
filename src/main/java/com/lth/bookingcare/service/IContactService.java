package com.lth.bookingcare.service;

import com.lth.bookingcare.dto.ContactDTO;
import com.lth.bookingcare.entity.Contact;

import java.util.List;

public interface IContactService {
    void deleteContact(Long id);
    Contact saveContact(ContactDTO contactDTO);
    List<Contact> getAllContacts();
}
