package ru.hh.superscoring.util.exceptions;

public class TestNoFilledException extends Exception {

  public TestNoFilledException(int num) {
    super("Not enough questions with weight " + num);
  }

  public TestNoFilledException(String message) {
    super(message);
  }
}
