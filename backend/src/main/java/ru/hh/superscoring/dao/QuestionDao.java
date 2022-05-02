package ru.hh.superscoring.dao;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

public class QuestionDao extends GenericDao {

  protected QuestionDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Transactional(readOnly = true)
  public List<Integer> getQuestionsForTest(Integer testId) {
    return getSession()
        .createQuery("select q.id from Question q where q.testId = :id", Integer.class)
        .setParameter("id", testId)
        .getResultList();
  }


}
