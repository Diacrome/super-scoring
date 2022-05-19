package ru.hh.superscoring.dao;


import java.util.Set;
import java.util.stream.Collectors;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.superscoring.entity.TestPassQuestion;

public class TestPassDao extends GenericDao {

  protected TestPassDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public boolean isExistUnfinishedRecord(Integer userId) {
    return getSession()
        .createQuery("select tp.id from TestPass tp where tp.userId = :user_id and tp.timeFinished is null", Integer.class)
        .setParameter("user_id", userId).setMaxResults(1).uniqueResult() != null;
  }

  public Set<TestPassQuestion> getTestPassQuestionsByUser(Integer userId) {
    return getSession()
        .createQuery("select q from TestPass tp join tp.questions q where tp.userId = :user_id and tp.timeFinished is null ", TestPassQuestion.class)
        .setParameter("user_id", userId)
        .getResultStream()
        .collect(Collectors.toSet());
  }

  @Transactional(readOnly = true)
  public Integer getTestPassByUserId(Integer userId) {
    return getSession()
        .createQuery("select r.id from TestPass r where r.userId = :user_id and r.timeFinished is null", Integer.class)
        .setParameter("user_id", userId)
        .uniqueResult();
  }

}

