package ru.hh.superscoring.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "test_pass_question_id")
public class TestPassQuestion {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "test_pass_id", nullable = false)
  private TestPass testPass;

  @Column(name = "question_id_order")
  private Integer questionIdOrder;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "question_id", nullable = false)
  private Question question;

  public TestPassQuestion() {
  }

  public TestPassQuestion(
      TestPass testPass,
      Integer questionIdOrder, Question question) {
    this.testPass = testPass;
    this.questionIdOrder = questionIdOrder;
    this.question = question;
  }

  public Integer getId() {
    return id;
  }

  public TestPass getTestPass() {
    return testPass;
  }

  public Integer getQuestionIdOrder() {
    return questionIdOrder;
  }

  public Question getQuestion() {
    return question;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setTestPass(TestPass testPass) {
    this.testPass = testPass;
  }

  public void setQuestionIdOrder(Integer questionIdOrder) {
    this.questionIdOrder = questionIdOrder;
  }

  public void setQuestion(Question question) {
    this.question = question;
  }
}
