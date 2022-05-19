package ru.hh.superscoring.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.hh.superscoring.entity.Question;

public class QuestionDto {
  private static final ObjectMapper mapper = new ObjectMapper();
  private String question;
  private JsonNode payload;

  public QuestionDto(String question, JsonNode payload) {
    this.question = question;
    this.payload = payload;
  }

  public QuestionDto() {
  }

  public static QuestionDto map(Question question) throws JsonProcessingException {
    if (question == null) {
      return new QuestionDto();
    }
    return new QuestionDto(question.getWording(), mapper.readValue(question.getPayload(), JsonNode.class));
  }

  public String getQuestion() {
    return question;
  }

  public JsonNode getPayload() {
    return payload;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public void setPayload(JsonNode payload) {
    this.payload = payload;
  }
}
