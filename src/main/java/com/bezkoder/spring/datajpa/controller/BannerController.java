package com.bezkoder.spring.datajpa.controller;

import com.bezkoder.spring.datajpa.model.Banner;
import com.bezkoder.spring.datajpa.repository.BannerRepository;
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
public class BannerController {

  @Autowired
  BannerRepository bannerRepository;

  @GetMapping("/banners")
  public ResponseEntity<List<Banner>> getAllBanners(
    @RequestParam(required = false) String title
  ) {
    try {
      List<Banner> banners = new ArrayList<Banner>();

      if (title == null) bannerRepository
        .findAll()
        .forEach(banners::add); else bannerRepository
        .findByTitleContaining(title)
        .forEach(banners::add);

      if (banners.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(banners, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/banners/{id}")
  public ResponseEntity<Banner> getBannerById(@PathVariable("id") long id) {
    Optional<Banner> bannerData = bannerRepository.findById(id);

    if (bannerData.isPresent()) {
      return new ResponseEntity<>(bannerData.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/banners")
  public ResponseEntity<Banner> createBanner(@RequestBody Banner banner) {
    try {
      Banner _banner = bannerRepository.save(
        new Banner(banner.getTitle(), banner.getDescription())
      );
      return new ResponseEntity<>(_banner, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/banners/{id}")
  public ResponseEntity<Banner> updateBanner(
    @PathVariable("id") long id,
    @RequestBody Banner banner
  ) {
    Optional<Banner> bannerData = bannerRepository.findById(id);

    if (bannerData.isPresent()) {
      Banner _banner = bannerData.get();
      _banner.setTitle(banner.getTitle());
      _banner.setDescription(banner.getDescription());
      return new ResponseEntity<>(bannerRepository.save(_banner), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/banners/{id}")
  public ResponseEntity<HttpStatus> deleteBanner(@PathVariable("id") long id) {
    try {
      bannerRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/banners")
  public ResponseEntity<HttpStatus> deleteAllBanners() {
    try {
      bannerRepository.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
