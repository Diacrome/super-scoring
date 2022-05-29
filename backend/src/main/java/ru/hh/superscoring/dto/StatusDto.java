package ru.hh.superscoring.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Map;

public class StatusDto {
  private boolean authorized;
  private CurrentPass currentPass;

  public boolean isAuthorized() {
    return authorized;
  }

  public void setAuthorized(boolean authorized) {
    this.authorized = authorized;
  }

  public CurrentPass getCurrentPass() {
    return currentPass;
  }

  public void setCurrentPass(CurrentPass currentPass) {
    this.currentPass = currentPass;
  }

  public static class CurrentPass {
    private Map<Integer, Boolean> answeredQuestions;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    private int testId;

    public Map<Integer, Boolean> getAnsweredQuestions() {
      return answeredQuestions;
    }

    public void setAnsweredQuestions(Map<Integer, Boolean> answeredQuestions) {
      this.answeredQuestions = answeredQuestions;
    }

    public LocalDateTime getStartTime() {
      return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
      this.startTime = startTime;
    }

    public int getTestId() {
      return testId;
    }

    public void setTestId(int testId) {
      this.testId = testId;
    }
  }
}
