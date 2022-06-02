package ru.hh.superscoring.dto;

import java.util.stream.IntStream;

import java.util.*;
import ru.hh.superscoring.entity.Question;

public class QuestionBoardDto {
  private Map<Integer, Question> questionMap = new HashMap<>();
  private Integer page;
  private Integer perPage;
  private int found;

  public QuestionBoardDto() {
  }

  public static QuestionBoardDto map(List<Question> questions, int page, int perPage) {
    QuestionBoardDto dto = new QuestionBoardDto();
    Map<Integer, Question> tmpQuestionMap = new HashMap<>();
    Integer offset = page * perPage + 1;
    IntStream.range(0, questions.size()).forEach(position -> tmpQuestionMap.put(position + offset, questions.get(position)));
    dto.setQuestionMap(tmpQuestionMap);
    dto.setPage(page);
    dto.setPerPage(perPage);
    dto.setFound(questions.size());
    return dto;
  }

  public Map<Integer, Question> getQuestionMap() {
    return questionMap;
  }

  public void setQuestionMap(Map<Integer, Question> questionMap) {
    this.questionMap = questionMap;
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
