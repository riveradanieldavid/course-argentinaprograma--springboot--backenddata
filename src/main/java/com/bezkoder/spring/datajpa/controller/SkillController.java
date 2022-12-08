package com.bezkoder.spring.datajpa.controller;

import com.bezkoder.spring.datajpa.model.Skill;
import com.bezkoder.spring.datajpa.repository.SkillRepository;
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
public class SkillController {

  @Autowired
  SkillRepository skillRepository;

  @GetMapping("/skills")
  public ResponseEntity<List<Skill>> getAllSkills(
    @RequestParam(required = false) String nameskill
  ) {
    try {
      List<Skill> skills = new ArrayList<Skill>();

      if (nameskill == null) skillRepository
        .findAll()
        .forEach(skills::add); else skillRepository
        .findByNameskillContaining(nameskill)
        .forEach(skills::add);

      if (skills.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(skills, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/skills/{id}")
  public ResponseEntity<Skill> getSkillById(@PathVariable("id") long id) {
    Optional<Skill> skillData = skillRepository.findById(id);

    if (skillData.isPresent()) {
      return new ResponseEntity<>(skillData.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/skills")
  public ResponseEntity<Skill> createSkill(@RequestBody Skill skill) {
    try {
      Skill _skill = skillRepository.save(
        new Skill(skill.getNameskill(), skill.getLevelskill())
      );
      return new ResponseEntity<>(_skill, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/skills/{id}")
  public ResponseEntity<Skill> updateSkill(
    @PathVariable("id") long id,
    @RequestBody Skill skill
  ) {
    Optional<Skill> skillData = skillRepository.findById(id);

    if (skillData.isPresent()) {
      Skill _skill = skillData.get();
      _skill.setNameskill(skill.getNameskill());
      _skill.setLevelskill(skill.getLevelskill());
      return new ResponseEntity<>(
        skillRepository.save(_skill),
        HttpStatus.OK
      );
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/skills/{id}")
  public ResponseEntity<HttpStatus> deleteSkill(@PathVariable("id") long id) {
    try {
      skillRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/skills")
  public ResponseEntity<HttpStatus> deleteAllSkills() {
    try {
      skillRepository.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
