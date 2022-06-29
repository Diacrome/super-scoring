package ru.hh.superscoring.service;


import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.hibernate.PropertyValueException;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.superscoring.dao.TestDao;
import ru.hh.superscoring.dao.TestPassDao;
import ru.hh.superscoring.dto.LeaderBoardDto;
import ru.hh.superscoring.dto.StartResultDto;
import ru.hh.superscoring.dto.TestPassDto;
import ru.hh.superscoring.entity.Question;
import ru.hh.superscoring.entity.Test;
import ru.hh.superscoring.entity.TestPass;
import ru.hh.superscoring.entity.TestPassQuestion;
import ru.hh.superscoring.exception.TestNoFilledException;
import ru.hh.superscoring.util.StartResult;
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
  public StartResultDto startTest(Integer testId, Integer userId) throws TestNoFilledException {

    StartResultDto startResultDto = new StartResultDto();

    if (testPassDao.isExistUnfinishedRecord(userId)) {
      startResultDto.setStartResult(StartResult.ALREADY_STARTED);
      return startResultDto;
    }

    List<TestPass> userTestPass = testPassDao.getTestPassByUserAndTestId(userId, testId);
    Test test = testDao.getTestById(testId);

    Optional<LocalDateTime> timeFinished =
        userTestPass.stream()
            .filter(tp -> (tp.getStatus().equals(TestPassStatus.PASSED)))
            .map(TestPass::getTimeFinished)
            .max(LocalDateTime::compareTo);
    if (timeFinished.isPresent() && timeFinished.get().isAfter(LocalDateTime.now().minusDays(test.getRepeatInterval()))) {
      startResultDto.setStartResult(StartResult.PASSED);
      startResultDto.setNextAttempt(
          timeFinished.get()
              .plusDays(test.getRepeatInterval() + 1)
              .toEpochSecond(ZoneOffset.of("+00:00")));
      return startResultDto;
    }

    Long attempts = userTestPass.stream()
        .filter(tp -> (tp.getTimeStarted().isAfter(LocalDateTime.now().minusDays(test.getRepeatInterval()))))
        .count();

    if (attempts >= test.getAttemptQuantity()) {
      startResultDto.setStartResult(StartResult.SPENT);
      startResultDto.setAttempts(test.getAttemptQuantity());
      startResultDto.setNextAttempt(
          userTestPass.stream()
              .map(TestPass::getTimeStarted)
              .max(LocalDateTime::compareTo).get()
              .plusDays(test.getRepeatInterval() + 1)
              .toEpochSecond(ZoneOffset.of("+00:00")));
      return startResultDto;
    }

    List<Question> questionsForStart = questionService.getQuestionsForTestByDistribution(testId);
    TestPass testPass = new TestPass();
    testPass.setTestId(testId);
    testPass.setUserId(userId);
    testPass.setStatus(TestPassStatus.PASS);
    testPass.setQuestions(questionsForStart);
    testPassDao.save(testPass);
    return startResultDto;
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

  @Transactional
  public void setTestPassStatusTimeoutByTestPassId(Integer testPassId) {
    testPassDao.get(TestPass.class, testPassId).setStatus(TestPassStatus.TIMEOUT);
  }

  @Transactional
  public void earlyCancelTestPassByUserId(Integer userId) {
    Integer testPassId = testPassDao.getTestPassByUserId(userId);
    if (testPassId == null) {
      throw (new PropertyValueException("No testPass for such user!", "testPassDao", "userId"));
    }
    testPassDao.get(TestPass.class, testPassId).setStatus(TestPassStatus.CANCELED);
  }
}
