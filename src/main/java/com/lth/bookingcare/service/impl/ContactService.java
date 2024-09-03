package com.lth.bookingcare.service.impl;

import com.lth.bookingcare.dto.ContactDTO;
import com.lth.bookingcare.entity.Contact;
import com.lth.bookingcare.exception.ResourceNotFoundException;
import com.lth.bookingcare.repository.ContactRepository;
import com.lth.bookingcare.service.IContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ContactService implements IContactService {
    @Autowired
    private ContactRepository contactRepository;
    @Override
    public void deleteContact(Long id) {
        if(contactRepository.findById(id).isEmpty())
            throw new ResourceNotFoundException("Not found with id: " + id);
        contactRepository.deleteById(id);
    }

    @Override
    public Contact saveContact(ContactDTO contactDTO) {
        Contact contact = new Contact();
        contact.setDob(contactDTO.getDob());
        contact.setName(contactDTO.getName());
        contact.setGmail(contactDTO.getEmail());
        contact.setPhone(contactDTO.getPhone());
        return contactRepository.save(contact);
    }

    @Override
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }
}
