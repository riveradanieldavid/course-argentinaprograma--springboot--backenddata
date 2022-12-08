package com.bezkoder.spring.datajpa.repository;

import com.bezkoder.spring.datajpa.model.Skill;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
  List<Skill> findByNameskillContaining(String nameskill);
}
