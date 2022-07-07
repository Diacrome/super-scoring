package ru.hh.superscoring.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.hibernate.PropertyValueException;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.superscoring.dao.QuestionDao;
import ru.hh.superscoring.dao.QuestionDistributionDao;
import ru.hh.superscoring.dao.TestDao;
import ru.hh.superscoring.entity.Question;
import ru.hh.superscoring.entity.QuestionDistribution;
import ru.hh.superscoring.util.JsonValidator;
import ru.hh.superscoring.util.exceptions.TestNoFilledException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class QuestionService {
  private final QuestionDao questionDao;
  private final QuestionDistributionDao questionDistributionDao;
  private final TestService testService;
  private final TestDao testDao;


  public QuestionService(QuestionDao questionDao, QuestionDistributionDao questionDistributionDao, TestService testService, TestDao testDao) {
    this.questionDao = questionDao;
    this.questionDistributionDao = questionDistributionDao;
    this.testService = testService;
    this.testDao = testDao;
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

  @Transactional(readOnly = true)
  public List<Question> getQuestionsForTestByDistribution(Integer testId) throws TestNoFilledException {
    List<QuestionDistribution> preassignedDistributions = questionDistributionDao.getAllQuestionDistributionsForTest(testId);
    List<Question> questions = questionDao.getQuestionsForTest(testId);
    validateTest(testDao.getTestSize(testId), preassignedDistributions, questions);
    Map<Integer, Integer> distributions = preassignedDistributions
        .stream().collect(Collectors.toMap(
            distribution -> distribution.getWeight(),
            distribution -> distribution.getQuestionCount()
        ));

    List<Question> finalQuestions = new ArrayList<>();
    Integer questionsNumberOfTest = distributions.values().stream().reduce(0, Integer::sum);

    while (questionsNumberOfTest > 0) {
      int currentIndex = (int) (Math.random() * questions.size());
      if (distributions.get(questions.get(currentIndex).getWeight()) > 0) {
        distributions.put(questions.get(currentIndex).getWeight(), distributions.get(questions.get(currentIndex).getWeight()) - 1);
        finalQuestions.add(questions.get(currentIndex));
        questionsNumberOfTest--;
      }
      Collections.swap(questions, currentIndex, questions.size() - 1);
      questions.remove(questions.size() - 1);
    }
    return finalQuestions;
  }

  private void validateTest(Integer testQuantity, List<QuestionDistribution> preassignedDistributions, List<Question> activeQuestions) throws TestNoFilledException {
    if (activeQuestions.size() < testQuantity) {
      throw new TestNoFilledException("Not enough questions for the test");
    }

    Map<Integer, Integer> realDistribution = activeQuestions.stream()
        .collect(Collectors.toMap(Question::getWeight, value -> 1, Integer::sum));

    for (QuestionDistribution questionDistribution : preassignedDistributions) {
      if (questionDistribution.getQuestionCount() > realDistribution.getOrDefault(questionDistribution.getWeight(), 0)) {
        throw new TestNoFilledException(questionDistribution.getWeight());
      }
    }
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
  public boolean addQuestion(Question newQuestion) throws IllegalArgumentException {
    try {
      if (!JsonValidator.verifyAnswer(newQuestion.getAnswer(), newQuestion.getPayload(), newQuestion.getAnswerType())) {
        throw new IllegalArgumentException("Answer is not valid");
      }
      if (!JsonValidator.verifyPayload(newQuestion.getPayload(), newQuestion.getAnswerType())) {
        throw new IllegalArgumentException("Payload is not valid");
      }
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException("Not correct JSON format");
    }
    Question question = new Question();
    question.setTestId(newQuestion.getTestId());
    question.setWording(newQuestion.getWording());
    question.setWeight(newQuestion.getWeight());
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

  public int saveQuestions(List<Question> questionList) {
    int questionsCounter = 0;
    for (Question question : questionList) {
      if (addQuestion(question)) {
        questionsCounter++;
      }
    }
    return questionsCounter;
  }
}
