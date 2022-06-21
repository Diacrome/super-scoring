package ru.hh.superscoring.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "answer")
public class Answer {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "test_pass_id")
  private Integer testPass;

  @Column(name = "question_order")
  private Integer questionOrder;

  @Column(name = "answer")
  private String answer;

  @Column(name = "time_answer")
  private LocalDateTime timeAnswer;

  public Integer getId(){
    return id;
  }

  public Integer getTestPass(){
    return testPass;
  }

  public void setTestPass(Integer testPassRecord){
    this.testPass = testPassRecord;
  }

  public Integer getQuestionOrder(){
    return questionOrder;
  }

  public void setQuestionOrder(Integer question){
    this.questionOrder = question;
  }

  public String getAnswer(){
    return answer;
  }

  public void setAnswer(String answer){
    this.answer = answer;
  }

  public LocalDateTime getTimeAnswer() {
    return timeAnswer;
  }

  public void setTimeAnswer(LocalDateTime timeAnswer){
    this.timeAnswer = timeAnswer;
  }
}
