package ru.hh.superscoring.dto;

import ru.hh.superscoring.entity.Test;

public class TestDto {
  private Integer id;
  private String name;
  private String description;
  private long timeLimit;

  public static TestDto map(Test test) {
    if (test == null) {
      return null;
    }
    TestDto testDto = new TestDto();
    testDto.setId(test.getId());
    testDto.setName(test.getName());
    testDto.setDescription(test.getDescription());
    testDto.setTimeLimit(test.getTimeLimit());
    return testDto;
  }

  public TestDto() {
  }

  public TestDto(Integer id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
  }

  public long getTimeLimit() {
    return timeLimit;
  }

  public void setTimeLimit(long timeLimit) {
    this.timeLimit = timeLimit;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
