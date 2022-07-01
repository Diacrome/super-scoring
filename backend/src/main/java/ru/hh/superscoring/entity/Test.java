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

  @Column(name = "question_quantity")
  private Integer questionQuantity;

  @Column(name = "attempt_quantity")
  private Integer attemptQuantity;

  @Column(name = "repeat_interval")
  private Integer repeatInterval;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "time_limit")
  private long timeLimit;

  public Test() {
  }

  public long getTimeLimit() {
    return timeLimit;
  }

  public Integer getQuestionQuantity() {
    return questionQuantity;
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

  public LocalDateTime getDateCreated() {
    return dateCreated;
  }

  public LocalDateTime getDateModified() {
    return dateModified;
  }

  public Integer getCreatorId() {
    return creatorId;
  }

  public Boolean getIsActive() {
    return isActive;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setCreatorId(Integer creatorId) {
    this.creatorId = creatorId;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setDateCreated(LocalDateTime dateCreated) {
    this.dateCreated = dateCreated;
  }

  public void setDateModified(LocalDateTime dateModified) {
    this.dateModified = dateModified;
  }

  public void setModifierId(Integer modifierId) {
    this.modifierId = modifierId;
  }

  public void setQuestionQuantity(Integer questionQuantity) {
    this.questionQuantity = questionQuantity;
  }

  public void setIsActive(Boolean isActive) {
    this.isActive = isActive;
  }

  public void setTimeLimit(Short timeLimit) {
    this.timeLimit = timeLimit;
  }

  public Integer getAttemptQuantity() {
    return attemptQuantity;
  }

  public Integer getRepeatInterval() {
    return repeatInterval;
  }

  public void setAttemptQuantity(Integer attemptQuantity) {
    this.attemptQuantity = attemptQuantity;
  }

  public void setRepeatInterval(Integer repeatInterval) {
    this.repeatInterval = repeatInterval;
  }
}
