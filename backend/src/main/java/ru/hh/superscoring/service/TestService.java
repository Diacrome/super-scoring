package ru.hh.superscoring.service;

import java.time.LocalDateTime;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.superscoring.dao.TestDao;
import ru.hh.superscoring.dto.TestDto;
import ru.hh.superscoring.entity.Test;

public class TestService {

  private final TestDao testDao;

  public TestService(TestDao testDao) {
    this.testDao = testDao;
  }

  public TestDto getTestById(Integer id) {
    return TestDto.map(testDao.get(Test.class, id));
  }

  @Transactional(readOnly = true)
  public boolean isExistTest(Integer testId) {
    return testDao.isExistTest(testId);
  }

  public Integer getTestSizeById(Integer testId) {
    return testDao.getTestSize(testId);
  }

  @Transactional
  public Integer saveTest(String name, String description, Integer creatorId, Integer questionCount) {
    Test test = new Test();
    test.setName(name);
    test.setDescription(description);
    test.setCreatorId(creatorId);
    test.setModifierId(creatorId);
    test.setQuestionQuantity(questionCount);
    test.setDateCreated(LocalDateTime.now());
    test.setDateModified(LocalDateTime.now());
    testDao.save(test);
    return test.getId();
  }

  @Transactional(readOnly = true)
  public Boolean isTestActive(Integer testId){
    return testDao.isTestActive(testId);
  }
}
