package ru.hh.superscoring.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import javax.annotation.Resource;
import ru.hh.superscoring.dao.AnswerDao;
import ru.hh.superscoring.dao.GenericDao;
import ru.hh.superscoring.entity.Answer;

public class AnswerService {
  private final AnswerDao answerDao;

  public AnswerService(AnswerDao answerDao){
    this.answerDao = answerDao;
  }

  public void saveAnswer(Integer userId, Integer questionId, String answerText){ // возможно стоит возвращать bool (ok?)
    Integer testPassRecordId = answerDao.getRecordByUserId(userId).get(); // тут, возможно, надо проверить на пустоту
    Answer answer = new Answer();
    answer.setTestPass(testPassRecordId);       // тут, возможно, надо проверить на наличие такого id
    answer.setQuestion(questionId);             // тут, возможно, надо проверить на наличие такого id
    answer.setAnswer(answerText);
    answer.setTimeAnswer(LocalDateTime.now());
    answerDao.save(answer);
  }
}
