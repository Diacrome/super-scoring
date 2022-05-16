package ru.hh.superscoring.dao;


import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.superscoring.entity.TestPass;

public class TestPassDao extends GenericDao {

  protected TestPassDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Transactional(readOnly = true)
  public boolean isExistUnfinishedRecord(Integer userId) {
    return !getSession()
        .createQuery("select r.id from TestPass r where r.userId = :user_id and r.timeFinished is null", Integer.class)
        .setParameter("user_id", userId).list().isEmpty();
  }

  @Transactional(readOnly = true)
  public List<Integer> getTestPassQuestionsByUser(Integer userId) {
    return getSession()
        .createQuery("select r from TestPass r where r.userId = :user_id and r.timeFinished is null ", TestPass.class)
        .setParameter("user_id", userId).getSingleResult().getQuestionIds();
  }

  @Transactional(readOnly = true)
  public Integer getTestPassByUserId(Integer userId) {
    return getSession()
        .createQuery("select r.id from TestPass r where r.userId = :user_id and r.timeFinished is null", Integer.class)
        .setParameter("user_id", userId)
        .uniqueResult();
  }

}

