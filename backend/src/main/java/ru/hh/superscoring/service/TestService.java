package ru.hh.superscoring.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.superscoring.dao.QuestionDao;
import ru.hh.superscoring.dao.QuestionDistributionDao;
import ru.hh.superscoring.dao.TestDao;
import ru.hh.superscoring.dto.TestDto;
import ru.hh.superscoring.entity.Question;
import ru.hh.superscoring.entity.QuestionDistribution;
import ru.hh.superscoring.entity.Test;
import ru.hh.superscoring.util.exceptions.TestNoFilledException;

public class TestService {

  private final TestDao testDao;
  private final QuestionDao questionDao;
  private final QuestionDistributionDao questionDistributionDao;

  public TestService(TestDao testDao, QuestionDao questionDao, QuestionDistributionDao questionDistributionDao) {
    this.testDao = testDao;
    this.questionDao = questionDao;
    this.questionDistributionDao = questionDistributionDao;
  }

  public TestDto getTestById(Integer id) {
    Test test = testDao.get(Test.class, id);
    if (test == null || !test.getIsActive()) {
      return null;
    }
    return TestDto.map(test);
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
    test.setIsActive(true);
    testDao.save(test);
    return test.getId();
  }

  @Transactional(readOnly = true)
  public boolean isExistActiveTest(Integer testId) {
    Boolean testState = testDao.isExistActiveTest(testId);
    return testState != null && testState;
  }

  @Transactional
  public void switchOffTest(Integer testId) {
    Test test = testDao.getTestById(testId);
    test.setIsActive(false);
    testDao.save(test);
  }

  @Transactional
  public void switchOnTest(Integer testId) throws TestNoFilledException {
    Test test = testDao.getTestById(testId);
    validateTest(test);
    test.setIsActive(true);
    testDao.save(test);
  }

  private void validateTest(Test test) throws TestNoFilledException {
    List<Question> activeQuestions = questionDao.getQuestionsForTest(test.getId());
    if (activeQuestions.size() < test.getQuestionQuantity()) {
      throw new TestNoFilledException("Not enough questions for the test");
    }

    List<QuestionDistribution> preassignedDistributions = questionDistributionDao.getAllQuestionDistributionsForTest(test.getId());

    Map<Integer, Integer> realDistribution = activeQuestions.stream()
        .collect(Collectors.toMap(Question::getWeight, value -> 1, Integer::sum));

    for (QuestionDistribution questionDistribution : preassignedDistributions) {
      if (questionDistribution.getQuestionCount() > realDistribution.getOrDefault(questionDistribution.getWeight(), 0)) {
        throw new TestNoFilledException(questionDistribution.getWeight());
      }
    }
  }

  @Transactional(readOnly = true)
  public List<Test> getAllTests(int page, int perPage) {
    return testDao.getAllTests(page, perPage);
  }

  @Transactional(readOnly = true)
  public List<TestDto> getAllTestsForUser(int page, int perPage) {
    return testDao.getAllTestsForUser(page, perPage);
  }

}
