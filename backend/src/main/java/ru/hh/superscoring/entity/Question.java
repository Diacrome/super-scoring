package ru.hh.superscoring.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "question")
public class Question {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "test_id")
  private Integer testId;

  @Column(name = "question_wording")
  private String wording;

  @Column(name = "payload")
  private String payload;

  @Column(name = "answer")
  private String answer;

  @Column(name = "question_content")
  private String content;

  @Column(name = "date_created")
  private LocalDateTime dateCreated;

  @Column(name = "date_modified")
  private LocalDateTime dateModified;

  @Column(name = "time_limit")
  private Short timeLimit;

  public Question() {
  }

  public Integer getId() {
    return id;
  }

  public Integer getTestId() {
    return testId;
  }

  public String getWording() {
    return wording;
  }

  public String getPayload() {
    return payload;
  }

  public String getAnswer() {
    return answer;
  }

  public String getContent() {
    return content;
  }

  public LocalDateTime getDateCreated() {
    return dateCreated;
  }

  public LocalDateTime getDateModified() {
    return dateModified;
  }

  public Short getTimeLimit() {
    return timeLimit;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setTestId(Integer testId) {
    this.testId = testId;
  }

  public void setWording(String wording) {
    this.wording = wording;
  }

  public void setPayload(String payload) {
    this.payload = payload;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void setDateCreated(LocalDateTime dateCreated) {
    this.dateCreated = dateCreated;
  }

  public void setDateModified(LocalDateTime dateModified) {
    this.dateModified = dateModified;
  }

  public void setTimeLimit(Short timeLimit) {
    this.timeLimit = timeLimit;
  }

}
