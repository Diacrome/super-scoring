package ru.hh.superscoring.service;

import java.time.LocalDateTime;
import java.util.List;

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
    Test test = testDao.get(Test.class, id);
    if (test == null || !test.getIsActive()) {
      return null;
    }
    return TestDto.map(test);
  }


  public Integer getTestSizeById(Integer testId) {
    return testDao.getTestSizeById(testId);
  }

  @Transactional
  public Integer saveNewTest(String name, String description, Integer creatorId, Integer questionCount) {
    Test test = new Test();
    test.setName(name);
    test.setDescription(description);
    test.setCreatorId(creatorId);
    test.setModifierId(creatorId);
    test.setQuestionQuantity(questionCount);
    test.setDateCreated(LocalDateTime.now());
    test.setDateModified(LocalDateTime.now());
    test.setIsActive(true);
    testDao.save(test);
    return test.getId();
  }

  @Transactional(readOnly = true)
  public boolean isTestActive(Integer testId) {
    Boolean testState = testDao.isTestActive(testId);
    return testState != null && testState;
  }

  @Transactional
  public void inactivateTestById(Integer testId) {
    Test test = testDao.getTestById(testId);
    test.setIsActive(false);
    testDao.save(test);
  }

  @Transactional
  public void activateTestById(Integer testId) {
    Test test = testDao.getTestById(testId);
    test.setIsActive(true);
    testDao.save(test);
  }

  @Transactional(readOnly = true)
  public List<Test> getAllTests(int page, int perPage) {
    return testDao.getAllTests(page, perPage);
  }
}
