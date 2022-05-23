package ru.hh.superscoring.service;

import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.PropertyValueException;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.superscoring.dao.AnswerDao;
import ru.hh.superscoring.dao.QuestionDao;
import ru.hh.superscoring.dao.TestDao;
import ru.hh.superscoring.dao.TestPassDao;
import ru.hh.superscoring.entity.Answer;
import ru.hh.superscoring.entity.Question;
import ru.hh.superscoring.entity.TestPass;

public class AnswerService {
  private final AnswerDao answerDao;
  private final TestPassDao testPassDao;
  private final TestDao testDao;
  private final QuestionDao questionDao;

  public AnswerService(AnswerDao answerDao, TestPassDao testPassDao, TestDao testDao, QuestionDao questionDao) {
    this.answerDao = answerDao;
    this.testPassDao = testPassDao;
    this.testDao = testDao;
    this.questionDao = questionDao;
  }


  public void saveAnswer(Integer userId, Integer questionOrder, String answerText) {
    Integer testPassId = testPassDao.getTestPassIdByUserId(userId);
    if (testPassId == null) {
      throw (new PropertyValueException("No testPass for such user!", "AnswerDao", "userId"));
    }
    Answer answer = new Answer();
    answer.setTestPass(testPassId);
    answer.setQuestion(questionOrder);
    answer.setAnswer(answerText);
    answer.setTimeAnswer(LocalDateTime.now());
    answerDao.save(answer);
    checkAndSetResultForTestPass(testPassId);
  }


  public void checkAndSetResultForTestPass(Integer testPassId) {
    List<Answer> listAnswerByTestPass = answerDao.getListAnswerByTestPassId(testPassId);
    if (testDao.getTestQuantityByTestPassId(testPassId) == listAnswerByTestPass.size()) {
      testPassDao.setResultForTestPass(testPassId);
    }
  }
}
