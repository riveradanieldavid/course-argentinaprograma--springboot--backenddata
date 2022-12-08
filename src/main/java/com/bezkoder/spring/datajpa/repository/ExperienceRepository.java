package com.bezkoder.spring.datajpa.repository;

import com.bezkoder.spring.datajpa.model.Experience;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
  List<Experience> findByTitleContaining(String title);
}
