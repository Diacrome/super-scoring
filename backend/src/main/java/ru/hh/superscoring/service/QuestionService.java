package ru.hh.superscoring.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
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
    Map<Integer,Integer> distributions = questionDistributionDao.getAllQuestionDistributionsForTest(testId)
        .stream().collect(Collectors.toMap(
            distribution -> distribution.getWeight(),
            distribution -> distribution.getQuestionCount()
        ));
    List<Question> questions = questionDao.getQuestionsForTest(testId);
    List<Question> finalQuestions = new ArrayList<>();
    Integer questionsNumberOfTest = distributions.values().stream().reduce(0,Integer::sum);

    while(questionsNumberOfTest > 0) {
      int currentIndex = (int) (Math.random() * questions.size());
      if (distributions.get(questions.get(currentIndex).getWeight()) > 0 ) {
        distributions.put(questions.get(currentIndex).getWeight(),distributions.get(questions.get(currentIndex).getWeight())-1);
        finalQuestions.add(questions.get(currentIndex));
        questionsNumberOfTest--;
      }
      Collections.swap(questions,currentIndex,questions.size()-1);
      questions.remove(questions.size()-1);
    }
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
