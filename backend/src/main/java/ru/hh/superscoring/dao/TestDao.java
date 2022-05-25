package ru.hh.superscoring.dao;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

public class TestDao extends GenericDao {

  protected TestDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Transactional(readOnly = true)
  public Integer getTestSize(Integer testId) {
    return getSession()
        .createQuery("select t.questionQuantity from Test t where t.id = :test_id", Integer.class)
        .setParameter("test_id", testId).getSingleResult();
  }

  public boolean isExistTest(Integer testId) {
    return getSession()
        .createQuery("select t.id from Test t where t.id = :test_id", Integer.class)
        .setParameter("test_id", testId).setMaxResults(1).uniqueResult() != null;
  }

  @Transactional(readOnly = true)
  public Integer getTestSizeByTestPassId(Integer testPassId) {
    return getSession()
        .createQuery("select t.questionQuantity from Test t where t.id = " +
            "(select tp.testId from TestPass tp where tp.id = :test_pass_id)", Integer.class)
        .setParameter("test_pass_id", testPassId)
        .uniqueResult();
  }
}
