package com.bezkoder.spring.datajpa.controller;

import com.bezkoder.spring.datajpa.model.About;
import com.bezkoder.spring.datajpa.repository.AboutRepository;
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
public class AboutController {

  @Autowired
  AboutRepository aboutRepository;

  @GetMapping("/abouts")
  public ResponseEntity<List<About>> getAllAbouts(
    @RequestParam(required = false) String title
  ) {
    try {
      List<About> abouts = new ArrayList<About>();

      if (title == null) aboutRepository
        .findAll()
        .forEach(abouts::add); else aboutRepository
        .findByTitleContaining(title)
        .forEach(abouts::add);

      if (abouts.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(abouts, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/abouts/{id}")
  public ResponseEntity<About> getAboutById(@PathVariable("id") long id) {
    Optional<About> aboutData = aboutRepository.findById(id);

    if (aboutData.isPresent()) {
      return new ResponseEntity<>(aboutData.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/abouts")
  public ResponseEntity<About> createAbout(@RequestBody About about) {
    try {
      About _about = aboutRepository.save(
        new About(about.getTitle(), about.getDescription())
      );
      return new ResponseEntity<>(_about, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/abouts/{id}")
  public ResponseEntity<About> updateAbout(
    @PathVariable("id") long id,
    @RequestBody About about
  ) {
    Optional<About> aboutData = aboutRepository.findById(id);

    if (aboutData.isPresent()) {
      About _about = aboutData.get();
      _about.setTitle(about.getTitle());
      _about.setDescription(about.getDescription());
      return new ResponseEntity<>(aboutRepository.save(_about), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/abouts/{id}")
  public ResponseEntity<HttpStatus> deleteAbout(@PathVariable("id") long id) {
    try {
      aboutRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/abouts")
  public ResponseEntity<HttpStatus> deleteAllAbouts() {
    try {
      aboutRepository.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
