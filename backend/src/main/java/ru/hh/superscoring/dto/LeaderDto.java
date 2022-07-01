package ru.hh.superscoring.dto;

public class LeaderDto {
  public String name;
  public Integer score;
  public Long attempts;


  public LeaderDto(String name, Integer score, Long attempts) {
    this.name = name;
    this.score = score;
    this.attempts = attempts;
  }

  public LeaderDto() {
  }

  public String toString() {
    return this.getName() + this.getScore() + this.getAttempts();
  }

  public String getName() {
    return name;
  }

  public Integer getScore() {
    return score;
  }

  public Long getAttempts() {
    return attempts;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setScore(Integer score) {
    this.score = score;
  }

  public void setAttempts(Long attempts) {
    this.attempts = attempts;
  }
}
