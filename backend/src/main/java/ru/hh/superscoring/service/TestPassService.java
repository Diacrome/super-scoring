package ru.hh.superscoring.service;


import java.util.List;
import java.util.Set;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.superscoring.dao.TestPassDao;
import ru.hh.superscoring.dto.LeaderBoardDto;
import ru.hh.superscoring.entity.Question;
import ru.hh.superscoring.entity.TestPass;
import ru.hh.superscoring.entity.TestPassQuestion;

public class TestPassService {
  private final TestPassDao testPassDao;
  private final QuestionService questionService;

  public TestPassService(TestPassDao testPassDao, QuestionService questionService, TestDao testDao) {
    this.testPassDao = testPassDao;
    this.questionService = questionService;
    this.testDao = testDao;
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

  @Transactional(readOnly = true)
  public LeaderBoardDto getLeaders(Integer testId, Integer page, Integer perPage){
    return LeaderBoardDto.map(testPassDao.getLeaders(testId, page, perPage), page, perPage, testPassDao.countLeadersForTest(testId));
  }
}
