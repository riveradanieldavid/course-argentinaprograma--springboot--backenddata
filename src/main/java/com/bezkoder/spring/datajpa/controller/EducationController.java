package com.bezkoder.spring.datajpa.controller;

import com.bezkoder.spring.datajpa.model.Education;
import com.bezkoder.spring.datajpa.repository.EducationRepository;
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
public class EducationController {

  @Autowired
  EducationRepository educationRepository;

  @GetMapping("/educations")
  public ResponseEntity<List<Education>> getAllEducations(
    @RequestParam(required = false) String title
  ) {
    try {
      List<Education> educations = new ArrayList<Education>();

      if (title == null) educationRepository
        .findAll()
        .forEach(educations::add); else educationRepository
        .findByTitleContaining(title)
        .forEach(educations::add);

      if (educations.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(educations, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/educations/{id}")
  public ResponseEntity<Education> getEducationById(@PathVariable("id") long id) {
    Optional<Education> educationData = educationRepository.findById(id);

    if (educationData.isPresent()) {
      return new ResponseEntity<>(educationData.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/educations")
  public ResponseEntity<Education> createEducation(@RequestBody Education education) {
    try {
      Education _education = educationRepository.save(
        new Education(education.getTitle(), education.getDescription())
      );
      return new ResponseEntity<>(_education, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/educations/{id}")
  public ResponseEntity<Education> updateEducation(
    @PathVariable("id") long id,
    @RequestBody Education education
  ) {
    Optional<Education> educationData = educationRepository.findById(id);

    if (educationData.isPresent()) {
      Education _education = educationData.get();
      _education.setTitle(education.getTitle());
      _education.setDescription(education.getDescription());
      return new ResponseEntity<>(educationRepository.save(_education), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/educations/{id}")
  public ResponseEntity<HttpStatus> deleteEducation(@PathVariable("id") long id) {
    try {
      educationRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/educations")
  public ResponseEntity<HttpStatus> deleteAllEducations() {
    try {
      educationRepository.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
