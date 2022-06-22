package ru.hh.superscoring.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import ru.hh.superscoring.entity.TestPass;
import ru.hh.superscoring.util.TestPassStatus;

public class TestPassDto {
  private Integer testPassId;
  private TestPassStatus status;
  private Integer finalScore;
  private Integer maxPossible;
  private String qualificationName;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime endDateTime;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime startDateTime;
  private Integer testId;
  private String testName;

  public static TestPassDto map(TestPass testPass) {
    if (testPass == null) {
      return null;
    }
    TestPassDto testPassDto = new TestPassDto();
    testPassDto.setTestPassId(testPass.getId());
    testPassDto.setStatus(testPass.getStatus());
    testPassDto.setFinalScore(testPass.getFinalScore());
    testPassDto.setMaxPossible(testPass.getMaxPossible());
    testPassDto.setQualificationName(testPass.getQualificationName());
    testPassDto.setEndDateTime(testPass.getTimeFinished());
    testPassDto.setStartDateTime(testPass.getTimeStarted());
    testPassDto.setTestId(testPass.getTestId());
    return testPassDto;
  }

  public TestPassDto() {
  }

  public String getTestName() {
    return testName;
  }

  public void setTestName(String testName) {
    this.testName = testName;
  }

  public Integer getTestId() {
    return testId;
  }

  public void setTestId(Integer testId) {
    this.testId = testId;
  }

  public Integer getTestPassId() {
    return testPassId;
  }

  public LocalDateTime getStartDateTime() {
    return startDateTime;
  }

  public void setStartDateTime(LocalDateTime startDateTime) {
    this.startDateTime = startDateTime;
  }

  public void setTestPassId(Integer testPassId) {
    this.testPassId = testPassId;
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

  public Integer getMaxPossible() {
    return maxPossible;
  }

  public void setMaxPossible(Integer maxPossible) {
    this.maxPossible = maxPossible;
  }

  public String getQualificationName() {
    return qualificationName;
  }

  public void setQualificationName(String qualificationName) {
    this.qualificationName = qualificationName;
  }

  public LocalDateTime getEndDateTime() {
    return endDateTime;
  }

  public void setEndDateTime(LocalDateTime endDateTime) {
    this.endDateTime = endDateTime;
  }
}
