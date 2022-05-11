package ru.hh.superscoring.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import javax.annotation.Resource;
import org.hibernate.PropertyValueException;
import ru.hh.superscoring.dao.AnswerDao;
import ru.hh.superscoring.dao.GenericDao;
import ru.hh.superscoring.entity.Answer;

public class AnswerService {
  private final AnswerDao answerDao;

  public AnswerService(AnswerDao answerDao){
    this.answerDao = answerDao;
  }

  public void saveAnswer(Integer userId, Integer questionOrder, String answerText){ // возможно стоит возвращать bool (isok?)
    Integer testPassId = answerDao.getRecordByUserId(userId);
    if (testPassId == null){
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
