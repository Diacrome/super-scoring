package ru.hh.superscoring.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import ru.hh.superscoring.entity.TestPassQuestion;

public class QuestionsForTestDto {

  private Map<Integer, QuestionDto> question = new HashMap<>();

  public QuestionsForTestDto() {
  }

  public static QuestionsForTestDto map(Set<TestPassQuestion> testPassQuestions) throws JsonProcessingException {
    QuestionsForTestDto dto = new QuestionsForTestDto();
    for (TestPassQuestion tpq : testPassQuestions) {
      dto.getQuestion().put(tpq.getQuestionIdOrder(), QuestionDto.map(tpq.getQuestion()));
    }
    return dto;
  }

  public Map<Integer, QuestionDto> getQuestion() {
    return question;
  }

  public void setQuestion(Map<Integer, QuestionDto> question) {
    this.question = question;
  }
}
