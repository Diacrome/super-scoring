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
      statusDto.setRole(authService.getRoleByToken(token));
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
    final Integer NumberOfQuestions = testDao.getTestSizeByTestPassId(testPassId);
    for (int i = 1; i <= NumberOfQuestions; i++) {
      questions.put(i, answeredQuestions.contains(i));
    }
    StatusDto.CurrentPass currentPass = new StatusDto.CurrentPass();
    currentPass.setAnsweredQuestions(questions);
    currentPass.setTestPassId(testPassId);
    currentPass.setStatus(testPassDao.getStatus(testPassId));
    currentPass.setTestId(testPassDao.getTestId(testPassId));
    currentPass.setStartTime(testPassDao.getStartTime(testPassId));
    return currentPass;
  }
}
