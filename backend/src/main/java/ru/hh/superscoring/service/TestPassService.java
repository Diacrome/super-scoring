package ru.hh.superscoring.service;


import java.util.List;
import java.util.Set;
import org.hibernate.HibernateException;
import org.hibernate.PropertyValueException;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.superscoring.dao.TestDao;
import ru.hh.superscoring.dao.TestPassDao;
import ru.hh.superscoring.dto.LeaderBoardDto;
import ru.hh.superscoring.dto.TestPassDto;
import ru.hh.superscoring.entity.Question;
import ru.hh.superscoring.entity.Test;
import ru.hh.superscoring.entity.TestPass;
import ru.hh.superscoring.entity.TestPassQuestion;
import ru.hh.superscoring.util.TestPassStatus;

public class TestPassService {
  private final TestPassDao testPassDao;
  private final QuestionService questionService;
  private final TestDao testDao;

  public TestPassService(TestPassDao testPassDao, QuestionService questionService, TestDao testDao) {
    this.testPassDao = testPassDao;
    this.questionService = questionService;
    this.testDao = testDao;
  }

  @Transactional
  public boolean startTest(Integer testId, Integer userId) {
    List<Question> questionsForStart = questionService.getQuestionsForStart(testId);
    if (questionsForStart.isEmpty()) {
      throw new HibernateException("Not enough questions for the test");
    }
    if (!testPassDao.isExistUnfinishedRecord(userId)) {
      TestPass testPass = new TestPass();
      testPass.setTestId(testId);
      testPass.setUserId(userId);
      testPass.setStatus(TestPassStatus.PASS);
      testPass.setQuestions(questionService.getQuestionsForStart(testId));
      testPassDao.save(testPass);
      return true;
    }
    return false;
  }

  @Transactional(readOnly = true)
  public Set<TestPassQuestion> getUnfinishedPassQuestions(Integer userId) {
    if (testPassDao.isExistUnfinishedRecord(userId)) {
      return testPassDao.getTestPassQuestionsByUser(userId);
    }
    return Set.of();
  }

  @Transactional(readOnly = true)
  public LeaderBoardDto getLeaders(Integer testId, Integer page, Integer perPage) {
    return LeaderBoardDto.map(testPassDao.getLeaders(testId, page, perPage), page, perPage, testPassDao.countLeadersForTest(testId));
  }

  @Transactional
  public void cancelTestPassByUserId(Integer userId) {
    Integer testPassId = testPassDao.getTestPassByUserId(userId);
    if (testPassId == null) {
      throw (new PropertyValueException("No testPass for such user!", "testPassDao", "userId"));
    }
    testPassDao.setTestPassStatusCanceled(testPassId);
  }

  @Transactional(readOnly = true)
  public TestPassDto getTestPassById(Integer testPassId) {
    TestPass testPass = testPassDao.get(TestPass.class, testPassId);
    if (testPass == null) {
      return null;
    }
    Test test = testDao.get(Test.class, testPass.getTestId());
    TestPassDto testPassDto = TestPassDto.map(testPass);
    testPassDto.setTestName(test.getName());
    return testPassDto;
  }
}
