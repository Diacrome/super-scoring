package ru.hh.superscoring.service;

import org.springframework.transaction.annotation.Transactional;
import ru.hh.superscoring.dao.AnswerDao;
import ru.hh.superscoring.dao.TestPassDao;
import ru.hh.superscoring.dto.StatusDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatusService {
  private final AuthService authService;
  private final TestPassDao testPassDao;
  private final AnswerDao answerDao;
  private static final int NUMBER_OF_QUESTIONS = 10;

  public StatusService(AuthService authService, TestPassDao testPassDao, AnswerDao answerDao) {
    this.authService = authService;
    this.testPassDao = testPassDao;
    this.answerDao = answerDao;
  }

  @Transactional(readOnly = true)
  public StatusDto getStatus(String token) {
    boolean authorized = true;
    Integer userId = authService.getUserIdWithToken(token);
    if (userId == null) {
      authorized = false;
    }
    StatusDto statusDto = new StatusDto();
    statusDto.setAuthorized(authorized);
    if (!authorized) {
      statusDto.setCurrentPass(null);
    } else {
      statusDto.setCurrentPass(getCurrentPass(userId));
    }
    return statusDto;
  }

  @Transactional(readOnly = true)
  public StatusDto.CurrentPass getCurrentPass(int userId) {
    Integer testPassId = testPassDao.getTestPassByUserId(userId);
    if (testPassId == null) {
      return null;
    }
    List<Integer> answeredQuestions = answerDao.getSequenceNumbersOfAnsweredQuestions(testPassId);
    Map<Integer, Boolean> questions = new HashMap<>();
    for (int i = 1; i <= NUMBER_OF_QUESTIONS; i++) {
      questions.put(i, answeredQuestions.contains(i));
    }
    StatusDto.CurrentPass currentPass = new StatusDto.CurrentPass();
    currentPass.setAnsweredQuestions(questions);
    currentPass.setTestId(testPassDao.getTestId(testPassId));
    currentPass.setStartTime(testPassDao.getStartTime(testPassId));
    return currentPass;
  }
}
