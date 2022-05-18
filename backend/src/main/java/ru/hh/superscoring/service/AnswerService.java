package ru.hh.superscoring.service;

import java.time.LocalDateTime;
import org.hibernate.PropertyValueException;
import ru.hh.superscoring.dao.AnswerDao;
import ru.hh.superscoring.dao.QuestionDao;
import ru.hh.superscoring.dao.TestDao;
import ru.hh.superscoring.dao.TestPassDao;
import ru.hh.superscoring.entity.Answer;
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
    if (testDao.getTestQuantity(testPassDao.getTestIdByUserId(userId)) == (questionOrder + 1)) {
      setResultOfTestPass(testPassId);
    }
  }

  public void setResultOfTestPass(Integer testPassId) {
    TestPass testPass = testPassDao.get(TestPass.class,testPassId);
    Integer valueTrueAnswer = 0;
    for(int index = 0;index < testDao.getTestQuantity(testPass.getTestId());++index){
      if(questionDao.getTrueAnswerOnQuestion(testPass.getQuestionIds().get(index))
                    .equals(answerDao.getAnswerByOrder(testPassId,index))) {
        valueTrueAnswer++;
      }
    }
    testPass.setValueTrueAnswer(valueTrueAnswer);
    testPass.setTimeFinished(LocalDateTime.now());
    testPassDao.save(testPass);
  }

}
