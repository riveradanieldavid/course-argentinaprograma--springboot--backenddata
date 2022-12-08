package com.bezkoder.spring.datajpa.controller;

import com.bezkoder.spring.datajpa.model.Experience;
import com.bezkoder.spring.datajpa.repository.ExperienceRepository;
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
public class ExperienceController {

  @Autowired
  ExperienceRepository experienceRepository;

  @GetMapping("/experiences")
  public ResponseEntity<List<Experience>> getAllExperiences(
    @RequestParam(required = false) String title
  ) {
    try {
      List<Experience> experiences = new ArrayList<Experience>();

      if (title == null) experienceRepository
        .findAll()
        .forEach(experiences::add); else experienceRepository
        .findByTitleContaining(title)
        .forEach(experiences::add);

      if (experiences.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(experiences, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/experiences/{id}")
  public ResponseEntity<Experience> getExperienceById(@PathVariable("id") long id) {
    Optional<Experience> experienceData = experienceRepository.findById(id);

    if (experienceData.isPresent()) {
      return new ResponseEntity<>(experienceData.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/experiences")
  public ResponseEntity<Experience> createExperience(@RequestBody Experience experience) {
    try {
      Experience _experience = experienceRepository.save(
        new Experience(experience.getTitle(), experience.getDescription())
      );
      return new ResponseEntity<>(_experience, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/experiences/{id}")
  public ResponseEntity<Experience> updateExperience(
    @PathVariable("id") long id,
    @RequestBody Experience experience
  ) {
    Optional<Experience> experienceData = experienceRepository.findById(id);

    if (experienceData.isPresent()) {
      Experience _experience = experienceData.get();
      _experience.setTitle(experience.getTitle());
      _experience.setDescription(experience.getDescription());
      return new ResponseEntity<>(experienceRepository.save(_experience), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/experiences/{id}")
  public ResponseEntity<HttpStatus> deleteExperience(@PathVariable("id") long id) {
    try {
      experienceRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/experiences")
  public ResponseEntity<HttpStatus> deleteAllExperiences() {
    try {
      experienceRepository.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
