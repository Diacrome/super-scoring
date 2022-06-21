package ru.hh.superscoring.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.hibernate.PropertyValueException;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.superscoring.dao.QuestionDao;
import ru.hh.superscoring.entity.Question;
import ru.hh.superscoring.util.JsonValidator;

public class QuestionService {
  private final QuestionDao questionDao;
  private final TestService testService;


  public QuestionService(QuestionDao questionDao, TestService testService) {
    this.questionDao = questionDao;
    this.testService = testService;
  }
  //ToDo: @Transactional?
  public List<Question> getGeneratedQuestionsForTest(Integer testId) {
    List<Question> allQuestions = questionDao.getAllActiveQuestionsByTestId(testId);
    Integer testSize = testService.getTestSizeById(testId);
    if (allQuestions.size() < testSize) {
      return List.of();
    }
    Collections.shuffle(allQuestions);
    return allQuestions.subList(0, testSize);
  }

  @Transactional
  public void unactivateQuestionById(Integer questionId) {
    Question question = questionDao.get(Question.class, questionId);
    if (question == null) {
      throw (new PropertyValueException("There is no question with such a QuestionId", "QuestionDao", "questionId"));
    }
    question.setActive(Boolean.FALSE);
    questionDao.save(question);
  }

  @Transactional
  public List<Question> getAllQuestionsByTestId(Integer testId, int page, int perPage) {
    return questionDao.getAllQuestionsByTestId(testId, page, perPage);
  }

  @Transactional
  public List<Question> getAllQuestions(int page, int perPage) {
    return questionDao.getAllQuestions(page, perPage);
  }

  @Transactional
  public void activateQuestionById(Integer questionId) {
    Question question = questionDao.get(Question.class, questionId);
    if (question == null) {
      throw (new PropertyValueException("There is no question with such a QuestionId", "QuestionDao", "questionId"));
    }
    question.setActive(Boolean.TRUE);
    questionDao.save(question);
  }

  //ToDo: возможно лучше просто проверять существует ли тест с таким id, достаточно специфичный метод
  @Transactional(readOnly = true)
  public Boolean ifExistsTestFromQuestion(Question question) {
    if (testService.isTestActive(question.getTestId()))
      return true;
    return false;
  }

  @Transactional
  public Boolean addNewQuestion(Question newQuestion) {
    try {
      if (JsonValidator.verifyAnswer(newQuestion.getAnswer(), newQuestion.getPayload(), newQuestion.getAnswerType())) {
        return false;
      }
    } catch (JsonProcessingException e) {
      return false;
    }
    Question question = new Question();
    question.setTestId(newQuestion.getTestId());
    question.setWording(newQuestion.getWording());
    question.setPayload(newQuestion.getPayload());
    question.setAnswer(newQuestion.getAnswer());
    question.setContent(newQuestion.getContent());
    question.setDateCreated(LocalDateTime.now());
    question.setDateModified(LocalDateTime.now());
    question.setTimeLimit(newQuestion.getTimeLimit());
    question.setAnswerType(newQuestion.getAnswerType());
    question.setActive(true);
    questionDao.save(question);
    return true;
  }

}
