package ru.hh.superscoring.dto;

import com.fasterxml.jackson.databind.JsonNode;

public class QuestionDto {
  private String question;
  private JsonNode payload;

  public QuestionDto(String question, JsonNode payload) {
    this.question = question;
    this.payload = payload;
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
