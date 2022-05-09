package ru.hh.superscoring.entity;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "test_pass")
public class TestPass {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "test_id")
  private Integer testId;

  @Column(name = "user_id")
  private Integer userId;

  @CreationTimestamp
  @Column(name = "time_started")
  private LocalDateTime timeStarted;

  @Column(name = "time_finished")
  private LocalDateTime timeFinished;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "test_pass_question_id", joinColumns=@JoinColumn(name="test_pass_id"))
  @OrderColumn (name = "question_id_order")
  @Column(name = "question_id")
  private List<Integer> questionIds = new ArrayList<Integer>();

  public TestPass() {
  }

  public Integer getId() {
    return id;
  }

  public Integer getTestId() {
    return testId;
  }

  public Integer getUserId() {
    return userId;
  }

  public LocalDateTime getTimeStarted() {
    return timeStarted;
  }

  public LocalDateTime getTimeFinished() {
    return timeFinished;
  }

  public List<Integer> getQuestionIds() {
    return questionIds;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setTestId(Integer testId) {
    this.testId = testId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public void setTimeStarted(LocalDateTime timeStarted) {
    this.timeStarted = timeStarted;
  }

  public void setTimeFinished(LocalDateTime timeFinished) {
    this.timeFinished = timeFinished;
  }

  public void setQuestionIds(List<Integer> questionIds) {
    this.questionIds = questionIds;
  }

}
