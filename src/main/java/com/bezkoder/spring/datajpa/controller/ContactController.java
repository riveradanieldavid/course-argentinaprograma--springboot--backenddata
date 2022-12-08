package com.bezkoder.spring.datajpa.controller;

import com.bezkoder.spring.datajpa.model.Contact;
import com.bezkoder.spring.datajpa.repository.ContactRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// HERE DEFINE PORT OF PRINCIPAL PAGE (Angular SPA)
// @CrossOrigin(origins = "http://localhost:8081")
@CrossOrigin // ALL URLS
@RestController
@RequestMapping("/api")
public class ContactController {

  @Autowired
  ContactRepository contactRepository;

  @GetMapping("/contacts")
  public ResponseEntity<List<Contact>> getAllContacts(
    @RequestParam(required = false) String title
  ) {
    try {
      List<Contact> contacts = new ArrayList<Contact>();

      if (title == null) contactRepository
        .findAll()
        .forEach(contacts::add); else contactRepository
        .findByTitleContaining(title)
        .forEach(contacts::add);

      if (contacts.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(contacts, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/contacts/{id}")
  public ResponseEntity<Contact> getContactById(@PathVariable("id") long id) {
    Optional<Contact> contactData = contactRepository.findById(id);

    if (contactData.isPresent()) {
      return new ResponseEntity<>(contactData.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/contacts")
  public ResponseEntity<Contact> createContact(@RequestBody Contact contact) {
    try {
      Contact _contact = contactRepository.save(
        new Contact(contact.getTitle(), contact.getDescription())
      );
      return new ResponseEntity<>(_contact, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/contacts/{id}")
  public ResponseEntity<Contact> updateContact(
    @PathVariable("id") long id,
    @RequestBody Contact contact
  ) {
    Optional<Contact> contactData = contactRepository.findById(id);

    if (contactData.isPresent()) {
      Contact _contact = contactData.get();
      _contact.setTitle(contact.getTitle());
      _contact.setDescription(contact.getDescription());
      return new ResponseEntity<>(contactRepository.save(_contact), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/contacts/{id}")
  public ResponseEntity<HttpStatus> deleteContact(@PathVariable("id") long id) {
    try {
      contactRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/contacts")
  public ResponseEntity<HttpStatus> deleteAllContacts() {
    try {
      contactRepository.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
