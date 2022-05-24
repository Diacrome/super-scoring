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

  public boolean isTestActive(Integer testId){
    return getSession()
        .createQuery("select is_active from Test where id = :test_id", Boolean.class)
        .setParameter("test_id", testId)
        .uniqueResult() != null;
  }

}
