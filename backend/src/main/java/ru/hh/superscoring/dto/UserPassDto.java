package ru.hh.superscoring.dto;

import java.time.LocalDateTime;
import ru.hh.superscoring.util.TestPassStatus;

public class UserPassDto extends TestPassDto {
  private String name;
  private String surname;
  private String patronymic;

  public UserPassDto() {
  }

  public UserPassDto(Integer testPassId,
                     TestPassStatus status,
                     Integer finalScore,
                     Integer maxPossible,
                     String qualificationName,
                     LocalDateTime endDateTime,
                     LocalDateTime startDateTime,
                     Integer testId,
                     String testName,
                     String name,
                     String surname,
                     String patronymic) {
    super(testPassId, status, finalScore, maxPossible, qualificationName, endDateTime, startDateTime, testId, testName);
    this.name = name;
    this.surname = surname;
    this.patronymic = patronymic;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getPatronymic() {
    return patronymic;
  }

  public void setPatronymic(String patronymic) {
    this.patronymic = patronymic;
  }
}
