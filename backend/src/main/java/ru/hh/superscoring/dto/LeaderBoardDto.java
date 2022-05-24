package ru.hh.superscoring.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class LeaderBoardDto {
  private Map<Integer, LeaderDto> leaders;
  private Integer page;
  private Integer perPage;
  private Long found;


  public static LeaderBoardDto map(List<LeaderDto> leadersList, Integer page, Integer perPage, Long found) {
    LeaderBoardDto dto = new LeaderBoardDto();
    Map<Integer, LeaderDto> leaders = new HashMap<>();
    Integer offset = page * perPage + 1;
    IntStream.range(0, leadersList.size()).forEach(position -> leaders.put(position + offset, leadersList.get(position)));
    dto.setLeaders(leaders);
    dto.setPage(page);
    dto.setPerPage(perPage);
    dto.setFound(found);
    return dto;
  }

  public LeaderBoardDto() {
  }

  public Map<Integer, LeaderDto> getLeaders() {
    return leaders;
  }

  public void setLeaders(Map<Integer, LeaderDto> leaders) {
    this.leaders = leaders;
  }

  public Integer getPage() {
    return page;
  }

  public Integer getPerPage() {
    return perPage;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public void setPerPage(Integer perPage) {
    this.perPage = perPage;
  }

  public Long getFound() {
    return found;
  }

  public void setFound(Long found) {
    this.found = found;
  }
}
