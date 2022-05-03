package ru.hh.superscoring.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.OptionalInt;

public class Validator {

  private static final Logger logger = LoggerFactory.getLogger(Validator.class);

  public static <TypeOfObject> OptionalInt validateId(Class<TypeOfObject> clazz, String id) {
    try {
      OptionalInt integerId = OptionalInt.of(Integer.parseInt(id));
      return integerId;
    } catch (NumberFormatException numberFormatException) {
      logger.error("{} - is not an integer value of the 'id' field of the {} table", id, clazz.getName());
      return OptionalInt.empty();
    }
  }
}
