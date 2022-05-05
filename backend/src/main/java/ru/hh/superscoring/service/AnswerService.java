package ru.hh.superscoring.service;

import java.sql.Timestamp;
import java.util.Date;
import ru.hh.superscoring.dao.GenericDao;
import ru.hh.superscoring.entity.Answer;

public class AnswerService {
  private final GenericDao answerDao;

  public AnswerService(GenericDao answerDao){
    this.answerDao = answerDao;
  }
  short took = 0;
  public void saveAnswer(Integer testPassRecordId, Integer questionId, String answerText){
    Answer answer = new Answer();
    answer.setTestPassRecord(testPassRecordId);
    answer.setQuestion(questionId);
    answer.setAnswer(answerText);
    answer.setTimeAnswer(new Timestamp(new Date().getTime()));
    answer.setTimeTook(took); // заглушка для параметра, который пока не ясно нужен ли
    answerDao.save(answer);
  }
}
