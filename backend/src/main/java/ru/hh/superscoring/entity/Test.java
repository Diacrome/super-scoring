package ru.hh.superscoring.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "test")
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

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public String getDateCreated() {
    return dateCreated.toString();
  }

  public String getDateModified() {
    return dateModified != null ? dateModified.toString() : "not modified yet";
  }

  public Integer getCreatorId() {
    return creatorId;
  }
}
