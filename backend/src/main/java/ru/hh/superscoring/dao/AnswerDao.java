package ru.hh.superscoring.dao;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.superscoring.entity.Answer;

public class AnswerDao extends GenericDao {

  protected AnswerDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Transactional(readOnly = true)
  public Long getValueAnswerByTestPassId(Integer testPassId) {
    return getSession()
        .createQuery("select count(a) from Answer a where a.testPass = :test_pass_id", Long.class)
        .setParameter("test_pass_id", testPassId)
        .uniqueResult();
  }

  @Transactional(readOnly = true)
  public List<Answer> getListAnswerByTestPassId(Integer testPassId) {
    return getSession()
        .createQuery("select a from Answer a where a.testPass = :test_pass_id", Answer.class)
        .setParameter("test_pass_id", testPassId)
        .getResultList();
  }
}
