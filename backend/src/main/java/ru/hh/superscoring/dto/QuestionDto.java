package ru.hh.superscoring.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.hh.superscoring.entity.Question;
import ru.hh.superscoring.util.QuestionAnswerType;

public class QuestionDto {
  private static final ObjectMapper mapper = new ObjectMapper();
  private String question;
  private JsonNode payload;
  private QuestionAnswerType answerType;
  private JsonNode content;

  public QuestionDto(String question, JsonNode payload, QuestionAnswerType answerType, JsonNode content) {
    this.question = question;
    this.payload = payload;
    this.answerType = answerType;
    this.content = content;
  }

  public QuestionDto() {
  }

  public static QuestionDto map(Question question) throws JsonProcessingException {
    if (question == null) {
      return new QuestionDto();
    }
    return new QuestionDto(
        question.getWording(),
        mapper.readValue(question.getPayload(), JsonNode.class),
        question.getAnswerType(),
        (question.getContent() == null) ? null : mapper.readValue(question.getContent(), JsonNode.class));
  }

  public QuestionAnswerType getAnswerType() {
    return answerType;
  }

  public void setAnswerType(QuestionAnswerType answerType) {
    this.answerType = answerType;
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

  public JsonNode getContent() {
    return content;
  }

  public void setContent(JsonNode content) {
    this.content = content;
  }
}
