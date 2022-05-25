package ru.hh.superscoring.dao;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.superscoring.entity.Question;

public class QuestionDao extends GenericDao {

  protected QuestionDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Transactional(readOnly = true)
  public List<Question> getQuestionsForTest(Integer testId) {
    return getSession()
        .createQuery("select q from Question q where q.testId = :id", Question.class)
        .setParameter("id", testId)
        .getResultList();
  }
}
