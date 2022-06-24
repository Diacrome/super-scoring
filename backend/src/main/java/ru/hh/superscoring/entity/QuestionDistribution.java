package ru.hh.superscoring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "question_distribution")
public class QuestionDistribution {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "test_id")
  private Integer testId;

  @Column(name = "weight")
  private Integer weight;

  @Column(name = "question_count")
  private Integer questionCount;

  public Integer getId() {
    return id;
  }

  public Integer getTestId() {
    return testId;
  }

  public Integer getWeight() {
    return weight;
  }

  public Integer getQuestionCount() {
    return questionCount;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setTestId(Integer testId) {
    this.testId = testId;
  }

  public void setWeight(Integer weight) {
    this.weight = weight;
  }

  public void setQuestionCount(Integer questionCount) {
    this.questionCount = questionCount;
  }

}
