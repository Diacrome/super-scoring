package ru.hh.superscoring.dto;

public class LeaderDto {
  public String name;
  public Integer score;


  public LeaderDto(String name, Integer score) {
    this.name = name;
    this.score = score;
  }

  public LeaderDto() {
  }

  public String toString() {
    return this.getName() + this.getScore();
  }

  public String getName() {
    return name;
  }

  public Integer getScore() {
    return score;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setScore(Integer score) {
    this.score = score;
  }
}
