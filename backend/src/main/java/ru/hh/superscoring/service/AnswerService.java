package ru.hh.superscoring.service;

import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.PropertyValueException;
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
    List<Answer> listAnswerByTestPass = answerDao.getListAnswerByTestPassId(testPassId);
    if (testDao.getTestQuantity(testPassDao.getTestIdByUserId(userId)) == listAnswerByTestPass.size()) {
      setResultOfTestPass(testPassId, listAnswerByTestPass);
    }

  }

  public void setResultOfTestPass(Integer testPassId, List<Answer> listAnswerByTestPass) {
    TestPass testPass = testPassDao.get(TestPass.class, testPassId);
    List<Question> listQuestionByTestPass = questionDao.getListQuestionByTestPassId(testPass.getQuestionIds());
    Integer valueTrueAnswer = 0;
    for (int i = 0; i < testDao.getTestQuantity(testPass.getTestId()); ++i) {
      final int index = i;
      if (listQuestionByTestPass.stream()
          .filter(question -> testPass.getQuestionIds().get(index).equals(question.getId()))
          .findAny()
          .get().getAnswer().equals(listAnswerByTestPass.get(index).getAnswer())) {
        valueTrueAnswer++;
      }
    }
    testPass.setValueTrueAnswer(valueTrueAnswer);
    testPass.setTimeFinished(LocalDateTime.now());
    testPassDao.save(testPass);
  }
}
