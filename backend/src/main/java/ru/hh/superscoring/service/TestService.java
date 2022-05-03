package ru.hh.superscoring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
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

  @Transactional
  public Optional<Test> getTestById(String id) {
    OptionalInt integerId = Validator.validateId(Test.class, id);
    if (integerId.isPresent()) {
      Test test = testDao.getTestById(integerId.getAsInt());
      if (test != null) {
        return Optional.of(test);
      } else {
        logger.error("There is no record with 'id' {} in the 'test' table!", id);
      }
    }
    return Optional.empty();
  }

}
