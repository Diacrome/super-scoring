package ru.hh.superscoring.service;


import java.util.List;
import java.util.Set;
import org.hibernate.HibernateException;
import org.hibernate.PropertyValueException;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.superscoring.dao.TestPassDao;
import ru.hh.superscoring.dto.LeaderBoardDto;
import ru.hh.superscoring.entity.Question;
import ru.hh.superscoring.entity.TestPass;
import ru.hh.superscoring.entity.TestPassQuestion;
import ru.hh.superscoring.util.TestPassStatus;

public class TestPassService {
  private final TestPassDao testPassDao;
  private final QuestionService questionService;

  public TestPassService(TestPassDao testPassDao, QuestionService questionService) {
    this.testPassDao = testPassDao;
    this.questionService = questionService;
  }

  @Transactional
  public boolean startTest(Integer testId, Integer userId) {
    List<Question> questionsForStart = questionService.getGeneratedQuestionsForTest(testId);
    if (questionsForStart.isEmpty()) {
      throw new HibernateException("Not enough questions for the test");
    }
    if (!testPassDao.isThereUnfinishedTestPassRecord(userId)) {
      TestPass testPass = new TestPass();
      testPass.setTestId(testId);
      testPass.setUserId(userId);
      testPass.setStatus(TestPassStatus.PASS);
      testPass.setQuestions(questionService.getGeneratedQuestionsForTest(testId));
      testPassDao.save(testPass);
      return true;
    }
    return false;
  }

  @Transactional(readOnly = true)
  public Set<TestPassQuestion> getUnfinishedTestPassQuestions(Integer userId) {
    if (testPassDao.isThereUnfinishedTestPassRecord(userId)) {
      return testPassDao.getTestPassUnansweredQuestionsByUserId(userId);
    }
    return Set.of();
  }

  @Transactional(readOnly = true)
  public LeaderBoardDto getLeadersByTestId(Integer testId, Integer page, Integer perPage) {
    return LeaderBoardDto.map(testPassDao.getLeadersByTestId(testId, page, perPage), page, perPage, testPassDao.countUsersWhoPassedTest(testId));
  }

  @Transactional
  public void cancelUnfinishedTestPassByUserId(Integer userId) {
    Integer testPassId = testPassDao.getUnfinishedTestPassIdByUserId(userId);
    if (testPassId == null) {
      throw (new PropertyValueException("No testPass for such user!", "testPassDao", "userId"));
    }
    testPassDao.setTestPassStatusCanceled(testPassId);
  }
}
