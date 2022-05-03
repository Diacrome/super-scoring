package ru.hh.superscoring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.superscoring.dao.TestDao;
import ru.hh.superscoring.entity.Test;
import ru.hh.superscoring.validator.Validator;
import java.util.Optional;
import java.util.OptionalInt;


public class TestService {

  private static final Logger logger = LoggerFactory.getLogger(TestService.class);
  private final TestDao testDao;

  public TestService(TestDao testDao) {
    this.testDao = testDao;
  }

  public Optional<Test> getTestById(String id) {
    OptionalInt integerId = Validator.validateId(Test.class, id);
    return integerId.isPresent() ? testDao.getTestById(integerId.getAsInt()) : Optional.empty();
  }
}
