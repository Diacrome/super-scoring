package ru.hh.superscoring.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Test {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;

  private String description;

  @Column(name = "date_created")
  private LocalDateTime dateCreated;

  @Column(name = "date_modified")
  private LocalDateTime dateModified;

  @Column(name = "creator_id")
  private Integer creatorId;

  @Column(name = "modifier_id")
  private Integer modifierId;

  public Test() {
  }

  public Integer getModifierId() {
    return modifierId;
  }

  public void setModifierId(Integer modifierId) {
    this.modifierId = modifierId;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDateCreated() {
    return dateCreated.toString();
  }

  public void setDateCreated(LocalDateTime dateCreated) {
    this.dateCreated = dateCreated;
  }

  public String getDateModified() {
    return dateModified != null ? dateModified.toString() : "not modified yet";
  }

  public void setDateModified(LocalDateTime dateModified) {
    this.dateModified = dateModified;
  }

  public Integer getCreatorId() {
    return creatorId;
  }

  public void setCreatorId(Integer creatorId) {
    this.creatorId = creatorId;
  }
}
