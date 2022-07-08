package ru.hh.superscoring.dao;

import org.hibernate.SessionFactory;
import ru.hh.superscoring.dto.LeaderDto;
import ru.hh.superscoring.dto.TestDto;
import ru.hh.superscoring.dto.TestPassDto;
import ru.hh.superscoring.dto.UserPassDto;
import ru.hh.superscoring.entity.Question;
import ru.hh.superscoring.entity.TestPass;
import ru.hh.superscoring.entity.TestPassQuestion;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import ru.hh.superscoring.util.TestPassStatus;

public class TestPassDao extends GenericDao {

  protected TestPassDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public boolean isExistUnfinishedRecord(Integer userId) {
    return getSession()
        .createQuery("select tp.id from TestPass tp where tp.userId = :user_id and tp.timeFinished is null and tp.status = 'PASS'", Integer.class)
        .setParameter("user_id", userId).setMaxResults(1).uniqueResult() != null;
  }

  public Set<TestPassQuestion> getTestPassQuestionsByUser(Integer userId) {
    return getSession()
        .createQuery("select q from TestPass tp join tp.questions q where tp.userId = :user_id and tp.timeFinished is null and tp.status = 'PASS' ", TestPassQuestion.class)
        .setParameter("user_id", userId)
        .getResultStream()
        .collect(Collectors.toSet());
  }

  public List<LeaderDto> getLeaders(Integer testId, Integer page, Integer perPage) {
    return getSession()
        .createQuery("select new ru.hh.superscoring.dto.LeaderDto(u.name, max(tp.finalScore), count(tp.userId)) " +
            "from TestPass as tp join User as u ON tp.userId = u.id " +
            "where tp.testId = :test_id and tp.finalScore is not null " +
            "group by tp.userId, u.name " +
            "order by max(tp.finalScore) desc, count(tp.userId) asc")
        .setParameter("test_id", testId)
        .setFirstResult(page * perPage)
        .setMaxResults(perPage)
        .getResultList();
  }

  public Long countLeadersForTest(Integer testId) {
    return getSession()
        .createQuery("select count(distinct tp.userId)  from TestPass as tp where tp.testId = :test_id and tp.finalScore is not null ", Long.class)
        .setParameter("test_id", testId)
        .getSingleResult();
  }

  public Integer getTestPassByUserId(Integer userId) {
    return getSession()
        .createQuery("select r.id from TestPass r where r.userId = :user_id and r.timeFinished is null and r.status = 'PASS'", Integer.class)
        .setParameter("user_id", userId)
        .uniqueResult();
  }

  public TestPass getTestPassByTestPassId(Integer testPassId) {
    return getSession()
        .createQuery("select testPass from TestPass testPass " +
            "left join fetch testPass.questions where testPass.id = :test_pass_id", TestPass.class)
        .setParameter("test_pass_id", testPassId)
        .uniqueResult();
  }

  public LocalDateTime getStartTime(Integer testPassId) {
    return getSession()
        .createQuery("SELECT t.timeStarted FROM TestPass t WHERE t.id = :id", LocalDateTime.class)
        .setParameter("id", testPassId)
        .getSingleResult();
  }

  public int getTestId(Integer testPassId) {
    return getSession()
        .createQuery("SELECT testId FROM TestPass WHERE id = :id", Integer.class)
        .setParameter("id", testPassId)
        .getSingleResult();
  }

  public Question getQuestionByQuestionIdOrderForUser(Integer userId, Integer questionIdOrder) {
    return getSession()
        .createQuery("select q from TestPassQuestion tpq join tpq.question q join tpq.testPass tp " +
            "where tp.userId = :user_id and tp.timeFinished is null and tp.status = 'PASS' and tpq.questionIdOrder = :question_id_order ", Question.class)
        .setParameter("user_id", userId)
        .setParameter("question_id_order", questionIdOrder)
        .uniqueResult();
  }

  public void setTestPassStatusCanceled(Integer testPassId) {
    String query = "UPDATE TestPass tp SET tp.status = 'CANCELED' WHERE id = " + testPassId;
    getSession().createQuery(query).executeUpdate();
  }

  public TestPassStatus getStatus(Integer testPassId) {
    return getSession()
        .createQuery("SELECT status FROM TestPass WHERE id = :id", TestPassStatus.class)
        .setParameter("id", testPassId)
        .getSingleResult();
  }

  public List<TestPass> getTestPassByUserAndTestId(Integer userId, Integer testId) {
    return getSession()
        .createQuery("select tp from TestPass tp where tp.userId = :user_id and tp.testId = :test_id", TestPass.class)
        .setParameter("user_id", userId)
        .setParameter("test_id", testId)
        .getResultList();
  }

  public List<TestPassDto> getAllTestPassesForUser(int page, int perPage, int testId, int userId) {
    return getSession()
        .createQuery("select new ru.hh.superscoring.dto" +
            ".TestPassDto(tp.id,tp.status,tp.finalScore,tp.maxPossible,tp.qualificationName,tp.timeFinished,tp.timeStarted,tp.testId,t.name) " +
            "from TestPass tp inner join Test t on tp.testId = t.id " +
            "where tp.testId = :testId and tp.userId = :userId order by tp.id asc", TestPassDto.class)
        .setParameter("testId", testId)
        .setParameter("userId", userId)
        .setFirstResult(page * perPage)
        .setMaxResults(perPage)
        .getResultList();
  }

  public UserPassDto getUserPassById(int testPassId) {
    return getSession()
        .createQuery("select new ru.hh.superscoring.dto" +
            ".UserPassDto(tp.id,tp.status,tp.finalScore,tp.maxPossible,tp.qualificationName,tp.timeFinished,tp.timeStarted,tp.testId,t.name,u.name,u.surname,u.patronymic) " +
            "from TestPass tp " +
            "inner join Test t on tp.testId = t.id " +
            "inner join User u on tp.userId = u.id " +
            "where tp.id = :testPassId", UserPassDto.class)
        .setParameter("testPassId", testPassId)
        .uniqueResult();
  }

}
