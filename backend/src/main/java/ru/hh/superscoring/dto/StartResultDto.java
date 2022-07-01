package ru.hh.superscoring.dto;

import java.time.LocalDate;
import ru.hh.superscoring.util.StartResult;

public class StartResultDto {
  private StartResult startResult;
  private Integer attempts;
  private Long nextAttempt;

  public StartResultDto() {
  }

  public StartResult getStartResult() {
    return startResult;
  }

  public Integer getAttempts() {
    return attempts;
  }

  public Long getNextAttempt() {
    return nextAttempt;
  }

  public void setStartResult(StartResult startResult) {
    this.startResult = startResult;
  }

  public void setAttempts(Integer attempts) {
    this.attempts = attempts;
  }

  public void setNextAttempt(Long nextAttempt) {
    this.nextAttempt = nextAttempt;
  }
}
