package ru.hh.superscoring.service;


import java.util.List;
import java.util.Set;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.superscoring.dao.TestPassDao;
import ru.hh.superscoring.entity.Question;
import ru.hh.superscoring.entity.TestPass;
import ru.hh.superscoring.entity.TestPassQuestion;

public class TestPassService {
  private final TestPassDao testPassDao;
  private final QuestionService questionService;

  public TestPassService(TestPassDao testPassDao, QuestionService questionService) {
    this.testPassDao = testPassDao;
    this.questionService = questionService;
  }

  @Transactional
  public boolean startTest(Integer testId, Integer userId) {
    List<Question> questionsForStart = questionService.getQuestionsForStart(testId);
    if (questionsForStart.isEmpty()) {
      return false;
    }
    if (!testPassDao.isExistUnfinishedRecord(userId)) {
      TestPass testPass = new TestPass();
      testPass.setTestId(testId);
      testPass.setUserId(userId);
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

}
