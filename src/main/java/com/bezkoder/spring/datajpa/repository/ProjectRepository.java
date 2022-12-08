package com.bezkoder.spring.datajpa.repository;

import com.bezkoder.spring.datajpa.model.Project;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
  List<Project> findByTitleContaining(String title);
}
