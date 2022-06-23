package ru.hh.superscoring.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;
import jnr.ffi.annotations.In;
import org.hibernate.PropertyValueException;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.superscoring.dao.QuestionDao;
import ru.hh.superscoring.dao.QuestionDistributionDao;
import ru.hh.superscoring.entity.Question;
import ru.hh.superscoring.entity.QuestionDistribution;
import ru.hh.superscoring.util.JsonValidator;

public class QuestionService {
  private final QuestionDao questionDao;
  private final QuestionDistributionDao questionDistributionDao;
  private final TestService testService;


  public QuestionService(QuestionDao questionDao, QuestionDistributionDao questionDistributionDao, TestService testService) {
    this.questionDao = questionDao;
    this.questionDistributionDao = questionDistributionDao;
    this.testService = testService;
  }

  @Transactional
  public void setQuestionNotActive(Integer questionId) {
    Question question = questionDao.get(Question.class, questionId);
    if (question == null) {
      throw (new PropertyValueException("There is no question with such a QuestionId", "QuestionDao", "questionId"));
    }
    question.setActive(Boolean.FALSE);
    questionDao.save(question);
  }

  @Transactional
  public List<Question> getAllQuestionsForTest(Integer testId, int page, int perPage) {
    return questionDao.getAllQuestionsForTest(testId, page, perPage);
  }

  @Transactional
  public List<Question> getAllQuestions(int page, int perPage) {
    return questionDao.getAllQuestions(page, perPage);
  }

  @Transactional
  public List<Question> getQuestionsForTestByDistribution(Integer testId) {
    List<QuestionDistribution> distributions = questionDistributionDao.getAllQuestionDistributionsForTest(testId);
    List<Question> questions = questionDao.getQuestionsForTest(testId);
    List<Question> finalQuestions = new ArrayList<Question>();

    for (QuestionDistribution distribution : distributions) {
      // формируем массив с вопросами определённого веса
      List<Question> tempListQuestions = new ArrayList<>(questions.stream()
          .filter(q -> q.getWeight() == distribution.getWeight())
          .collect(Collectors.toList()));
      // формируем множество со всевозможными значениями индекса в массиве с вопросами определённого веса
      Set<Integer> allIndexes = new HashSet<>();
      for (int index = 0; index < tempListQuestions.size(); index++) {
        allIndexes.add(index);
      }
      int countGeneratedIndexes = 0;
      while (countGeneratedIndexes < distribution.getQuestionCount()) {
        int currentIndex = (int) (Math.random() * tempListQuestions.size());
        if (allIndexes.contains(currentIndex)) {
          // если случайный индекс массива ещё не использовался, то используем его и удаляем из множества
          // всевозможных значений индекса массива,чтобы он уже не повторялся,
          // так делаем до тех пор, пока не наберём нужно количество вопросов
          countGeneratedIndexes++;
          allIndexes.remove(currentIndex);
          finalQuestions.add(tempListQuestions.get(currentIndex));
        }
      }
    }
    Collections.shuffle(finalQuestions);
    return finalQuestions;
  }

  @Transactional
  public void setQuestionActive(Integer questionId) {
    Question question = questionDao.get(Question.class, questionId);
    if (question == null) {
      throw (new PropertyValueException("There is no question with such a QuestionId", "QuestionDao", "questionId"));
    }
    question.setActive(Boolean.TRUE);
    questionDao.save(question);
  }

  @Transactional(readOnly = true)
  public Boolean ifExistsTestFromQuestion(Question question) {
    if (testService.isExistActiveTest(question.getTestId()))
      return true;
    return false;
  }

  @Transactional
  public Boolean addQuestion(Question newQuestion) {
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
