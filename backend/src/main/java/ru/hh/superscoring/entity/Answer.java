package ru.hh.superscoring.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Answers")
public class Answer {

  @Id
  @Column(name = "answer_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "test_pass_record_id")
  private Integer testPassRecord;

  @Column(name = "question_id")
  private Integer question;

  @Column(name = "answer")
  private String answer;

  @Column(name = "time_answer")
  private Timestamp timeAnswer;

  @Column(name = "time_took")
  private Short timeTook;

  public Integer getId(){
    return id;
  }

  public Integer getTestPassRecord(){
    return testPassRecord;
  }

  public void setTestPassRecord(Integer testPassRecord){
    this.testPassRecord = testPassRecord;
  }

  public Integer getQuestion(){
    return question;
  }

  public void setQuestion(Integer question){
    this.question = question;
  }

  public String getAnswer(){
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

  public Timestamp getTimeAnswer() {
    return timeAnswer;
  }

  public void setTimeAnswer(Timestamp timeAnswer) {
    this.timeAnswer = timeAnswer;
  }

  public Short getTimeTook(){
    return timeTook;
  }

  public void setTimeTook(Short timeTook) {
    this.timeTook = timeTook;
  }
}
