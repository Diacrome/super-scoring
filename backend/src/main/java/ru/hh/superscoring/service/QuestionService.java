package ru.hh.superscoring.service;


import java.util.Collections;
import java.util.List;
import org.hibernate.PropertyValueException;
import ru.hh.superscoring.dao.QuestionDao;
import ru.hh.superscoring.entity.Question;

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

  public void setQuestionNotActivity(Integer questionId) {
    Question question = questionDao.get(Question.class, questionId);
    if (question == null) {
      throw (new PropertyValueException("There is no question with such a QuestionId", "QuestionDao", "questionId"));
    }
    question.setActivity(Boolean.FALSE);
    questionDao.save(question);
  }
}
