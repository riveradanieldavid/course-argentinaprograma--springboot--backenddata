package com.bezkoder.spring.datajpa.repository;

import com.bezkoder.spring.datajpa.model.Education;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationRepository extends JpaRepository<Education, Long> {
  List<Education> findByTitleContaining(String title);
}
