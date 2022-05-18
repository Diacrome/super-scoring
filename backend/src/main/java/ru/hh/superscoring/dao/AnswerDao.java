package ru.hh.superscoring.dao;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

public class AnswerDao extends GenericDao {

  protected AnswerDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Transactional(readOnly = true)
  public String getAnswerByOrder(Integer testPassId, Integer questionOrder) {
    return getSession()
        .createQuery("select a.answer from Answer a where a.testPass = :test_pass_id and a.question = :question_order", String.class)
        .setParameter("test_pass_id", testPassId)
        .setParameter("question_order", questionOrder)
        .uniqueResult();
  }
}
