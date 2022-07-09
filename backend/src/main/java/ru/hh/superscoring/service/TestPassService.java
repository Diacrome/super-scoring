package ru.hh.superscoring.service;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;
import org.hibernate.PropertyValueException;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.superscoring.dao.TestDao;
import ru.hh.superscoring.dao.TestPassDao;
import ru.hh.superscoring.dao.UserDao;
import ru.hh.superscoring.dto.LeaderBoardDto;
import ru.hh.superscoring.dto.StartResultDto;
import ru.hh.superscoring.dto.StatisticDto;
import ru.hh.superscoring.dto.TestPassBoardDto;
import ru.hh.superscoring.dto.TestPassDto;
import ru.hh.superscoring.dto.UserPassDto;
import ru.hh.superscoring.entity.Question;
import ru.hh.superscoring.entity.Test;
import ru.hh.superscoring.entity.TestPass;
import ru.hh.superscoring.entity.TestPassQuestion;
import ru.hh.superscoring.entity.User;
import ru.hh.superscoring.util.Chart;
import ru.hh.superscoring.util.exceptions.TestNoFilledException;
import ru.hh.superscoring.util.StartResult;
import ru.hh.superscoring.util.TestPassStatus;

public class TestPassService {
  private final TestPassDao testPassDao;
  private final QuestionService questionService;
  private final TestDao testDao;
  private final UserDao userDao;

  public TestPassService(TestPassDao testPassDao, QuestionService questionService, TestDao testDao, UserDao userDao) {
    this.testPassDao = testPassDao;
    this.questionService = questionService;
    this.testDao = testDao;
    this.userDao = userDao;
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
    UserPassDto userPassDto = testPassDao.getUserPassById(testPassId);
    if (userPassDto == null) {
      return null;
    }
    return userPassDto;
  }

  @Transactional
  public void setTestPassStatusTimeoutByTestPassId(Integer testPassId) {
    testPassDao.get(TestPass.class, testPassId).setStatus(TestPassStatus.TIMEOUT);
  }

  @Transactional(readOnly = true)
  public TestPassBoardDto getAllTestPassesForUser(int page, int perPage, int testId, int userId) {
    List<TestPassDto> listTestPassDto = testPassDao.getAllTestPassesForUser(page, perPage, testId, userId);
    TestPassBoardDto testPassBoardDto = TestPassBoardDto.map(listTestPassDto, page, perPage);
    User user = userDao.get(User.class, userId);
    testPassBoardDto.setName(user.getName());
    testPassBoardDto.setSurname(user.getSurname());
    testPassBoardDto.setPatronymic(user.getPatronymic());
    return testPassBoardDto;
  }

  @Transactional(readOnly = true)
  public File getChartForResult(int testPassId) throws IOException, FontFormatException, PropertyValueException {
    UserPassDto userPass = testPassDao.getUserPassById(testPassId);
    if (userPass.getFinalScore() == null) {
      throw new PropertyValueException("No result fo this pass!", "TestPassDao", "testPassId");
    }
    Integer pivotFinalScore = userPass.getFinalScore();
    List<StatisticDto> data = testPassDao.getDataForChartByTestId(userPass.getTestId());

    Long[] resultVector = new Long[userPass.getMaxPossible() + 1];
    Arrays.fill(resultVector, 0L);
    data.stream().forEach(stdto -> resultVector[stdto.finalScore] = stdto.getNumberOfResults());

    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    IntStream.rangeClosed(1, userPass.getMaxPossible()).forEach(
        score -> dataset.addValue(
            resultVector[score],
            (score < pivotFinalScore ? "прошли тест хуже вас    " : "прошли тест не хуже вас"),
            Integer.toString(score)));

    return Chart.createBarChart(dataset);
  }
}
