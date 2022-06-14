package ru.hh.superscoring.entity;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CreationTimestamp;
import ru.hh.superscoring.util.QuestionAnswerType;
import ru.hh.superscoring.util.TestPassStatus;

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

  @Cascade(CascadeType.SAVE_UPDATE)
  @OneToMany(mappedBy = "testPass", fetch = FetchType.LAZY)
  private Set<TestPassQuestion> questions = new HashSet<>();

  @Column(name = "final_score")
  private Integer finalScore;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private TestPassStatus status;

  public TestPass() {
  }

  public TestPassStatus getStatus() {
    return status;
  }

  public void setStatus(TestPassStatus status) {
    this.status = status;
  }

  public Integer getFinalScore() {
    return finalScore;
  }

  public void setFinalScore(Integer finalScore) {
    this.finalScore = finalScore;
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

  public Set<TestPassQuestion> getQuestions() {
    return questions;
  }

  public void setQuestions(List<Question> questions) {
    this.questions = Stream.iterate(1, x -> x + 1)
        .limit(questions.size())
        .map(order -> new TestPassQuestion(
            this,
            order, questions.get(order - 1)))
        .collect(Collectors.toSet());
  }
}
