package ru.hh.superscoring.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.PropertyValueException;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.superscoring.dao.AnswerDao;
import ru.hh.superscoring.dao.TestDao;
import ru.hh.superscoring.dao.TestPassDao;
import ru.hh.superscoring.entity.Answer;
import ru.hh.superscoring.entity.Question;
import ru.hh.superscoring.entity.TestPass;
import ru.hh.superscoring.entity.TestPassQuestion;
import ru.hh.superscoring.util.JsonValidator;
import ru.hh.superscoring.util.TestPassStatus;

public class AnswerService {
  private final AnswerDao answerDao;
  private final TestPassDao testPassDao;
  private final TestDao testDao;

  public AnswerService(AnswerDao answerDao, TestPassDao testPassDao, TestDao testDao) {
    this.answerDao = answerDao;
    this.testPassDao = testPassDao;
    this.testDao = testDao;
  }

  @Transactional
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
    checkAndSetResultForTestPass(testPassId);
  }

  private void checkAndSetResultForTestPass(Integer testPassId) {
    List<Answer> arrayAnswerByTestPass = answerDao.getListAnswerByTestPassId(testPassId);
    if (testDao.getTestSizeByTestPassId(testPassId) == arrayAnswerByTestPass.size()) {
      TestPass testPass = testPassDao.getTestPassByTestPassId(testPassId);
      Integer finalScore = 0;
      for (TestPassQuestion testPassQuestion : testPass.getQuestions()) {
        if (testPassQuestion.getQuestion().getAnswer().equals(
            arrayAnswerByTestPass.get(testPassQuestion.getQuestionIdOrder() - 1).getAnswer())) {
          finalScore++;
        }
      }
      testPass.setFinalScore(finalScore);
      testPass.setStatus(TestPassStatus.PASSED);
      testPass.setTimeFinished(LocalDateTime.now());
      testPassDao.save(testPass);
    }
  }

  @Transactional(readOnly = true)
  public boolean validateAnswer(String answer, Integer userId, Integer questionIdOrder) throws JsonProcessingException, PropertyValueException {
    Question question = testPassDao.getQuestionByQuestionIdOrderForUser(userId, questionIdOrder);
    if (question == null) {
      throw new HibernateException("The user was not asked a question with this number");
    }
    return JsonValidator.verifyAnswer(answer, question.getPayload(), question.getAnswerType());
  }

}
