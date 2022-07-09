package ru.hh.superscoring.dto;

public class StatisticDto {
  public Integer finalScore;
  public Long numberOfResults;

  public StatisticDto(int finalScore, Long numberOfResults) {
    this.finalScore = finalScore;
    this.numberOfResults = numberOfResults;
  }

  public Integer getFinalScore() {
    return finalScore;
  }

  public Long getNumberOfResults() {
    return numberOfResults;
  }

  public void setFinalScore(Integer finalScore) {
    this.finalScore = finalScore;
  }

  public void setNumberOfResults(Long numberOfResults) {
    this.numberOfResults = numberOfResults;
  }
}
