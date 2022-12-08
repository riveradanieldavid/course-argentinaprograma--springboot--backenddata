package com.bezkoder.spring.datajpa.repository;

import com.bezkoder.spring.datajpa.model.Contact;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
  List<Contact> findByTitleContaining(String title);
}
