package ru.hh.superscoring.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.PropertyValueException;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.superscoring.dao.AnswerDao;
import ru.hh.superscoring.dao.QualificationDao;
import ru.hh.superscoring.dao.TestDao;
import ru.hh.superscoring.dao.TestPassDao;
import ru.hh.superscoring.entity.Answer;
import ru.hh.superscoring.entity.Question;
import ru.hh.superscoring.entity.Test;
import ru.hh.superscoring.entity.TestPass;
import ru.hh.superscoring.entity.TestPassQuestion;
import ru.hh.superscoring.util.JsonValidator;
import ru.hh.superscoring.util.TestPassStatus;
import ru.hh.superscoring.util.exceptions.TestPassTimeoutException;

public class AnswerService {
  private final AnswerDao answerDao;
  private final TestPassDao testPassDao;
  private final TestDao testDao;
  private final QualificationDao qualificationDao;
  private final TestPassService testPassService;
  private final Integer ONE_HUNDRED_PERCENT = 1;


  public AnswerService(AnswerDao answerDao, TestPassDao testPassDao, TestDao testDao, QualificationDao qualificationDao, TestPassService testPassService) {
    this.answerDao = answerDao;
    this.testPassDao = testPassDao;
    this.testDao = testDao;
    this.qualificationDao = qualificationDao;
    this.testPassService = testPassService;
  }

  @Transactional
  public void saveAnswer(Integer userId, Integer questionOrder, String answerText) throws TestPassTimeoutException {
    Integer testPassId = testPassDao.getTestPassByUserId(userId);
    if (testPassId == null) {
      throw (new PropertyValueException("No testPass for such user!", "AnswerDao", "userId"));
    }
    Test test = testDao.getTestById(testPassDao.getTestId(testPassId));
    LocalDateTime testPassTimeDiffer = testPassDao.getTestPassByTestPassId(testPassId).getTimeStarted().plusSeconds(test.getTimeLimit());
    if (LocalDateTime.now().isAfter(testPassTimeDiffer)) {
      testPassService.setTestPassStatusTimeoutByTestPassId(userId);
      throw new TestPassTimeoutException("Test pass time limit exceeded!");
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
      Integer maxPossible = 0;
      for (TestPassQuestion testPassQuestion : testPass.getQuestions()) {
        if (CheckAnswer(testPassQuestion.getQuestion().getAnswer()
            , arrayAnswerByTestPass.get(testPassQuestion.getQuestionIdOrder() - 1).getAnswer())) {
          finalScore += testPassQuestion.getQuestion().getWeight();
        }
        maxPossible += testPassQuestion.getQuestion().getWeight();
      }
      testPass.setFinalScore(finalScore);
      testPass.setStatus(TestPassStatus.PASSED);
      testPass.setTimeFinished(LocalDateTime.now());
      testPass.setMaxPossible(maxPossible);
      testPass.setQualificationName(qualificationCalculation(finalScore, maxPossible, testPass.getTestId()));
      testPassDao.save(testPass);
    }
  }

  public static boolean CheckAnswer(String rightAnswer, String givenAnswer) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      JsonNode rightAnswerJson = mapper.readTree(rightAnswer);
      JsonNode givenAnswerJson = mapper.readTree(givenAnswer);
      if (rightAnswerJson.isEmpty() || givenAnswerJson.isEmpty()) {
        return false;
      }
      return rightAnswerJson.equals(givenAnswerJson);
    } catch (JsonProcessingException jpe) {
      return false;
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

  private String qualificationCalculation(Integer finalScore, Integer maxPossible, Integer testId) {
    List<String> qualificationNames = qualificationDao.getTestQualification(testId);
    double percentStep = (double) ONE_HUNDRED_PERCENT / (qualificationNames.size() + 1);
    double percentResult = (double) finalScore / maxPossible;
    if (percentResult < percentStep) {
      return "Not qualified";
    }
    if ((percentResult <= 1.0) && (percentResult > (1.0 - percentStep))) {
      return qualificationNames.get(0);
    }
    int index = 0;
    for (double stepNumber = percentStep; stepNumber <= percentResult; stepNumber += percentStep) {
      index++;
    }
    return qualificationNames.get(qualificationNames.size() - index);
  }
}
