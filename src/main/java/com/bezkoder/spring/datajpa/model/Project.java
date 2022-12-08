package com.bezkoder.spring.datajpa.model;

import javax.persistence.*;

// GET DATA FROM DB (tutorials table) AND SAVED IN Tutorial CLASS
@Entity
@Table(name = "projects")
public class Project {
  // SET IDENTIFICATOR AND COLUMNS TO USE
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(name = "title")
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "link")
  private String link;

  @Column(name = "linkdescription")
  private String linkdescription;

  // DEFINE EACH tutorial
  public Project() {}

  public Project(
    String title,
    String description,
    String link,
    String linkdescription
  ) {
    this.title = title;
    this.description = description;
    this.link = link;
    this.linkdescription = linkdescription;
  }

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

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public String getLinkdescription() {
    return linkdescription;
  }

  public void setLinkdescription(String linkdescription) {
    this.linkdescription = linkdescription;
  }

  // ASSEMBLE THE "Tutorial"
  @Override
  public String toString() {
    return (
      "Project [id=" +
      id +
      ", title=" +
      title +
      ", desc=" +
      description +
      ", link=" +
      link +
      ", linkdescription=" +
      linkdescription +
      "]"
    );
  }
}
