package ru.hh.superscoring.service;

import org.springframework.transaction.annotation.Transactional;
import ru.hh.superscoring.dao.AnswerDao;
import ru.hh.superscoring.dao.TestDao;
import ru.hh.superscoring.dao.TestPassDao;
import ru.hh.superscoring.dto.StatusDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatusService {
  private final AuthService authService;
  private final TestPassDao testPassDao;
  private final AnswerDao answerDao;
  private final TestDao testDao;


  public StatusService(AuthService authService, TestPassDao testPassDao, AnswerDao answerDao,TestDao testDao) {
    this.authService = authService;
    this.testPassDao = testPassDao;
    this.answerDao = answerDao;
    this.testDao = testDao;
  }

  @Transactional(readOnly = true)
  public StatusDto getStatusByToken(String token) {
    boolean authorized = true;
    Integer userId = authService.getUserIdByToken(token);
    if (userId == null) {
      authorized = false;
    }
    StatusDto statusDto = new StatusDto();
    statusDto.setAuthorized(authorized);
    if (!authorized) {
      statusDto.setCurrentPass(null);
    } else {
      statusDto.setCurrentPass(getCurrentTestPass(userId));
    }
    return statusDto;
  }

  @Transactional(readOnly = true)
  public StatusDto.CurrentPass getCurrentTestPass(int userId) {
    Integer testPassId = testPassDao.getUnfinishedTestPassIdByUserId(userId);
    if (testPassId == null) {
      return null;
    }
    List<Integer> answeredQuestions = answerDao.getAnsweredQuestionOrderList(testPassId);
    Map<Integer, Boolean> questions = new HashMap<>();
    final Integer NumberOfQuestions = testDao.getTestSizeByTestPassId(testPassId);
    for (int i = 1; i <= NumberOfQuestions; i++) {
      questions.put(i, answeredQuestions.contains(i));
    }
    StatusDto.CurrentPass currentPass = new StatusDto.CurrentPass();
    currentPass.setAnsweredQuestions(questions);
    currentPass.setStatus(testPassDao.getTestPassStatusByTestPassId(testPassId));
    currentPass.setTestId(testPassDao.getTestIdByTestPassId(testPassId));
    currentPass.setStartTime(testPassDao.getTestPassStartTimeByTestPassId(testPassId));
    return currentPass;
  }
}
