package ru.hh.superscoring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.superscoring.dao.GenericDao;
import ru.hh.superscoring.entity.Test;
import java.util.Optional;

public class TestService {

  private static final Logger logger = LoggerFactory.getLogger(TestService.class);
  private final GenericDao genericDao;

  public TestService(GenericDao genericDao) {
    this.genericDao = genericDao;
  }

  public Optional<Test> getTestById(Integer id) {
    Test test = genericDao.get(Test.class, id);
    if (test != null) {
      return Optional.of(test);
    } else {
      logger.error("There is no record with 'id' {} in the 'test' table!", id);
      return Optional.empty();
    }
  }
}
