package ru.hh.superscoring.dto;

public class Model {
  private String name;
  private String message;

  public Model() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Model(String name, String message) {
    this.name = name;
    this.message = message;
  }
}

