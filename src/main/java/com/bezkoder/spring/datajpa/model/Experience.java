package com.bezkoder.spring.datajpa.model;

import javax.persistence.*;

// GET DATA FROM DB (tutorials table) AND SAVED IN Tutorial CLASS
@Entity
@Table(name = "experiences")
public class Experience {

  // SET IDENTIFICATOR AND COLUMNS TO USE
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(name = "title")
  private String title;

  @Column(name = "description")
  private String description;

  // DEFINE EACH tutorial
  public Experience() {}

  public Experience(String title, String description) {
    this.title = title;
    this.description = description;
  }

  // ADDED
  // ASSOCIATE "images_has_experiences" with "image_id" AND "experience_id"
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
    name = "images_has_experiences",
    joinColumns = @JoinColumn(name = "images_id"),
    inverseJoinColumns = @JoinColumn(name = "experiences_id")
  )
  // ADDED /

  // GETTER AND SETTER
  public long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  // ASSEMBLE THE "Tutorial"
  @Override
  public String toString() {
    return (
      "Experience [id=" +
      id +
      ", title=" +
      title +
      ", desc=" +
      description +
      "]"
    );
  }
}
