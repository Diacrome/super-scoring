package ru.hh.superscoring.dto;

import ru.hh.superscoring.entity.Test;

public class TestDto {
  private Integer id;
  private String name;
  private String description;

  public static TestDto map(Test test) {
    if (test == null) {
      return null;
    }
    TestDto testDto = new TestDto();
    testDto.setId(test.getId());
    testDto.setName(test.getName());
    testDto.setDescription(test.getDescription());
    return testDto;
  }

  public TestDto() {
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
