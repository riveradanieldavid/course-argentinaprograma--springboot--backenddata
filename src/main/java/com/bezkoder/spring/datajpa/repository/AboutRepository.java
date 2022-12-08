package com.bezkoder.spring.datajpa.repository;

import com.bezkoder.spring.datajpa.model.About;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AboutRepository extends JpaRepository<About, Long> {
  List<About> findByTitleContaining(String title);
}
