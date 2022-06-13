package ru.hh.superscoring.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.PropertyValueException;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.superscoring.dao.QuestionDao;
import ru.hh.superscoring.entity.Question;
import ru.hh.superscoring.util.JsonValidator;
import ru.hh.superscoring.util.QuestionAnswerType;

import javax.ws.rs.core.MultivaluedMap;

public class QuestionService {
  private final QuestionDao questionDao;
  private final TestService testService;


  public QuestionService(QuestionDao questionDao, TestService testService) {
    this.questionDao = questionDao;
    this.testService = testService;
  }

  public List<Question> getQuestionsForStart(Integer testId) {
    List<Question> allQuestions = questionDao.getQuestionsForTest(testId);
    Integer testSize = testService.getTestSizeById(testId);
    if (allQuestions.size() < testSize) {
      return List.of();
    }
    Collections.shuffle(allQuestions);
    return allQuestions.subList(0, testSize);
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

  public Map<Integer, Boolean> saveQuestionsFromForm(int testId, MultivaluedMap<String, String> formParams) throws JsonProcessingException {
    Map<Integer,Boolean> questionRecordsLog = new LinkedHashMap<>();
//    int questionCount = Integer.parseInt(formParams.get("question-counter").get(0));
//    for (int i = 1; i <= questionCount; i++) {
//      Map<Integer,String> answerOptions = new LinkedHashMap<>();
//      int j = 1;
//      while (!formParams.get("answer" + i + "-" + j).isEmpty()) {
//        answerOptions.put(j, formParams.get("answer" + i + "-" + j).get(0));
//        j++;
//      }
//      Question question = new Question();
//      question.setTestId(testId);
//      question.setTimeLimit(Short.parseShort(formParams.get("time-limit").get(0)));
//      question.setWeight(formParams.get("weight"));
//      question.setWording(formParams.get("question" + i).get(0));
//      question.setAnswerType(QuestionAnswerType.valueOf(formParams.get("answer-type" + i).get(0)));
//      question.setPayload(new ObjectMapper().writeValueAsString(answerOptions));
//      question.setAnswer(buildAnswer(answerOptions, question.getAnswerType()));
//      question.setContent();
//      saveQuestionTable.put(i, questionService.addQuestion(question);
//    }
    return questionRecordsLog;
  }

//  private String buildAnswer(Map<Integer, String> answerOptions, QuestionAnswerType answerType) {
//    return
//  }

}
