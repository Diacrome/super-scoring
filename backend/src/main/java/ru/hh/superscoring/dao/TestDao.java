package ru.hh.superscoring.dao;

import java.util.Optional;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.superscoring.entity.Test;

public class TestDao extends GenericDao {

  private static final Logger logger = LoggerFactory.getLogger(TestDao.class);

  public TestDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Transactional(readOnly = true)
  public Optional<Test> getTestById(Integer id) {
    Test test = get(Test.class, id);
    if (test != null) {
      return Optional.of(test);
    } else {
      logger.error("There is no record with 'id' {} in the 'test' table!!!", id);
      return Optional.empty();
    }
  }

}
