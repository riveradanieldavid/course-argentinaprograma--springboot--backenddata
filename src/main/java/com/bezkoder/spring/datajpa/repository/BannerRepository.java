package com.bezkoder.spring.datajpa.repository;

import com.bezkoder.spring.datajpa.model.Banner;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerRepository extends JpaRepository<Banner, Long> {
  List<Banner> findByTitleContaining(String title);
}
