package ru.hh.superscoring.service;

import java.time.LocalDateTime;
import org.hibernate.PropertyValueException;
import ru.hh.superscoring.dao.AnswerDao;
import ru.hh.superscoring.dao.TestPassDao;
import ru.hh.superscoring.entity.Answer;

public class AnswerService {
  private final AnswerDao answerDao;
  private final TestPassDao testPassDao;

  public AnswerService(AnswerDao answerDao, TestPassDao testPassDao) {
    this.answerDao = answerDao;
    this.testPassDao = testPassDao;
  }

  public void saveAnswer(Integer userId, Integer questionOrder, String answerText) {
    Integer testPassId = testPassDao.getTestPassByUserId(userId);
    if (testPassId == null) {
      throw (new PropertyValueException("No testPass for such user!", "AnswerDao", "userId"));
    }
    Answer answer = new Answer();
    answer.setTestPass(testPassId);
    answer.setQuestion(questionOrder);
    answer.setAnswer(answerText);
    answer.setTimeAnswer(LocalDateTime.now());
    answerDao.save(answer);
  }
}
