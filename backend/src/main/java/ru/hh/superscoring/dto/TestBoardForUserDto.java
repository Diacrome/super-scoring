package ru.hh.superscoring.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class TestBoardForUserDto {
  private Map<Integer, TestDto> testMap = new HashMap<>();
  private Integer page;
  private Integer perPage;
  private int found;

  public TestBoardForUserDto() {
  }

  public static TestBoardForUserDto map(List<TestDto> tests, int page, int perPage) {
    TestBoardForUserDto dto = new TestBoardForUserDto();
    Map<Integer, TestDto> tmpTestMap = new HashMap<>();
    Integer offset = page * perPage + 1;
    IntStream.range(0, tests.size()).forEach(position -> tmpTestMap.put(position + offset, tests.get(position)));
    dto.setTestMap(tmpTestMap);
    dto.setPage(page);
    dto.setPerPage(perPage);
    dto.setFound(tests.size());
    return dto;
  }

  public Map<Integer, TestDto> getTestMap() {
    return testMap;
  }

  public void setTestMap(Map<Integer, TestDto> testMap) {
    this.testMap = testMap;
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
}
