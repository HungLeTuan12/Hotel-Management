package com.lth.bookingcare.controller;

import com.lth.bookingcare.dto.ContactDTO;
import com.lth.bookingcare.entity.Contact;
import com.lth.bookingcare.service.impl.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import response.ErrorResponse;
import response.SuccessResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ContactController {
    @Autowired
    private ContactService contactService;
    @GetMapping("/contacts")
    public ResponseEntity<?> getAllContact() {
        try {
            List<Contact> contacts = contactService.getAllContacts();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Contacts", contacts));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse<>("Server error !"));
        }
    }
    @PostMapping("/create-contact")
    public ResponseEntity<?> createContact(@RequestBody ContactDTO contactDTO) {
        try {
            Contact contact = contactService.saveContact(contactDTO);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Create contact successfully ! "));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse<>("Server error !"));
        }
    }
    @DeleteMapping("/delete-contact/{id}")
    public ResponseEntity<?> deleteContact(@PathVariable("id") Long id) {
        try {
            contactService.deleteContact(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Delete contact successfully ! "));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse<>("Server error !"));
        }
    }
}
