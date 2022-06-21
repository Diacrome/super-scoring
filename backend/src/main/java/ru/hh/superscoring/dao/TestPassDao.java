package ru.hh.superscoring.dao;

import org.hibernate.SessionFactory;
import ru.hh.superscoring.dto.LeaderDto;
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

  public boolean isThereUnfinishedTestPassRecord(Integer userId) {
    return getSession()
        .createQuery("select tp.id from TestPass tp where tp.userId = :user_id and tp.timeFinished is null and tp.status = 'PASS'", Integer.class)
        .setParameter("user_id", userId).setMaxResults(1).uniqueResult() != null;
  }

  public Set<TestPassQuestion> getTestPassUnansweredQuestionsByUserId(Integer userId) {
    return getSession()
        .createQuery("select q from TestPass tp join tp.questions q where tp.userId = :user_id and tp.timeFinished is null and tp.status = 'PASS' ", TestPassQuestion.class)
        .setParameter("user_id", userId)
        .getResultStream()
        .collect(Collectors.toSet());
  }

  public List<LeaderDto> getLeadersByTestId(Integer testId, Integer page, Integer perPage) {
    return getSession()
        .createQuery("select new ru.hh.superscoring.dto.LeaderDto(u.name, max(tp.finalScore)) " +
            "from TestPass as tp join User as u ON tp.userId = u.id " +
            "where tp.testId = :test_id and tp.finalScore is not null " +
            "group by tp.userId, u.name " +
            "order by max(tp.finalScore) desc")
        .setParameter("test_id", testId)
        .setFirstResult(page * perPage)
        .setMaxResults(perPage)
        .getResultList();
  }

  public Long countUsersWhoPassedTest(Integer testId) {
    return getSession()
        .createQuery("select count(distinct tp.userId)  from TestPass as tp where tp.testId = :test_id and tp.finalScore is not null ", Long.class)
        .setParameter("test_id", testId)
        .getSingleResult();
  }

  public Integer getUnfinishedTestPassIdByUserId(Integer userId) {
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

  public LocalDateTime getTestPassStartTimeByTestPassId(Integer testPassId) {
    return getSession()
        .createQuery("SELECT t.timeStarted FROM TestPass t WHERE t.id = :id", LocalDateTime.class)
        .setParameter("id", testPassId)
        .getSingleResult();
  }

  public int getTestIdByTestPassId(Integer testPassId) {
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
    String query = "UPDATE TestPass tp SET tp.status = 'CANCELED' WHERE id = "+testPassId;
    getSession().createQuery(query).executeUpdate();
  }

  public TestPassStatus getTestPassStatusByTestPassId(Integer testPassId) {
    return getSession()
        .createQuery("SELECT status FROM TestPass WHERE id = :id", TestPassStatus.class)
        .setParameter("id", testPassId)
        .getSingleResult();
  }
}
