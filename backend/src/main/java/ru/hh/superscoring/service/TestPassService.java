package ru.hh.superscoring.service;

import java.util.List;
import ru.hh.superscoring.dao.TestPassDao;
import ru.hh.superscoring.entity.TestPass;

public class TestPassService {
  private final TestPassDao testPassDao;
  private final QuestionService questionService;
  private static final int testSize = 10;

  public TestPassService(TestPassDao testPassDao, QuestionService questionService) {
    this.testPassDao = testPassDao;
    this.questionService = questionService;
  }

  public Boolean startTest(Integer testID, Integer userId) {
    if (!testPassDao.isExistUnfinishedRecord(userId)) {
      TestPass testPass = new TestPass();
      testPass.setTestId(testID);
      testPass.setUserId(userId);
      testPass.setQuestionIds(questionService.getQuestionsForStart(testID, testSize));
      testPassDao.save(testPass);
      return true;}
    return false;
  }

  public List<Integer> getUnfinishedPassQuestions(Integer userId) {
    if (testPassDao.isExistUnfinishedRecord(userId)) {
      return testPassDao.getTestPassQuestionsByUser(userId);
    }
    return List.of();
  }
}
