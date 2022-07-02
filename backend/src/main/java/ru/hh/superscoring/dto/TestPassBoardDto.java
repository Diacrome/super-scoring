package ru.hh.superscoring.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class TestPassBoardDto {
  private Map<Integer, TestPassDto> testPassMap = new HashMap<>();
  private Integer page;
  private Integer perPage;
  private int found;
  private String name;
  private String surname;
  private String patronymic;

  public TestPassBoardDto() {
  }

  public static TestPassBoardDto map(List<TestPassDto> testPasses, int page, int perPage) {
    TestPassBoardDto dto = new TestPassBoardDto();
    Map<Integer, TestPassDto> tmpTestPassMap = new HashMap<>();
    Integer offset = page * perPage + 1;
    IntStream.range(0, testPasses.size()).forEach(position -> tmpTestPassMap.put(position + offset, testPasses.get(position)));
    dto.setTestPassMap(tmpTestPassMap);
    dto.setPage(page);
    dto.setPerPage(perPage);
    dto.setFound(testPasses.size());
    return dto;
  }

  public Map<Integer, TestPassDto> getTestMap() {
    return testPassMap;
  }

  public void setTestPassMap(Map<Integer, TestPassDto> testMap) {
    this.testPassMap = testMap;
  }

  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public Integer getPerPage() {
    return perPage;
  }

  public void setPerPage(Integer perPage) {
    this.perPage = perPage;
  }

  public int getFound() {
    return found;
  }

  public void setFound(int found) {
    this.found = found;
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
