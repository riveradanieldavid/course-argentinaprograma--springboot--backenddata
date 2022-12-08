package com.bezkoder.spring.datajpa.model;

import javax.persistence.*;

// GET DATA FROM DB (tutorials table) AND SAVED IN Tutorial CLASS
@Entity
@Table(name = "skills")
public class Skill {

  // SET IDENTIFICATOR AND COLUMNS TO USE
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(name = "nameskill")
  private String nameskill;

  @Column(name = "levelskill")
  private Integer levelskill;

  // DEFINE EACH tutorial
  public Skill() {}

  public Skill(String nameskill, Integer levelskill) {
    this.nameskill = nameskill;
    this.levelskill = levelskill;
  }

  // GETTER AND SETTER
  public long getId() {
    return id;
  }

  public String getNameskill() {
    return nameskill;
  }

  public void setNameskill(String nameskill) {
    this.nameskill = nameskill;
  }

  public Integer getLevelskill() {
    return levelskill;
  }

  public void setLevelskill(Integer levelskill) {
    this.levelskill = levelskill;
  }

  // ASSEMBLE THE "Tutorial"
  @Override
  public String toString() {
    return (
      "Skill [id=" + id + ", nameskill=" + nameskill + ", desc=" + levelskill + "]"
    );
  }
}
