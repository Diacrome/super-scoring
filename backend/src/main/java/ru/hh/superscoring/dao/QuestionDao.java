package ru.hh.superscoring.dao;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.superscoring.entity.Question;

public class QuestionDao extends GenericDao {

  protected QuestionDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }
  //ToDo: баг убрать transactional?
  @Transactional(readOnly = true)
  public List<Question> getAllActiveQuestionsByTestId(Integer testId) {
    return getSession()
        .createQuery("select q from Question q where q.testId = :id and q.active = true", Question.class)
        .setParameter("id", testId)
        .getResultList();
  }

  public List<Question> getAllQuestionsByTestId(Integer testId, int page, int perPage) {
    return getSession()
        .createQuery("select q from Question q where q.testId = :id " +
            "order by q.id asc", Question.class)
        .setParameter("id", testId)
        .setFirstResult(page * perPage)
        .setMaxResults(perPage)
        .getResultList();
  }

  public List<Question> getAllQuestions(int page, int perPage) {
    return getSession()
        .createQuery("select q from Question q " +
            "order by q.testId asc", Question.class)
        .setFirstResult(page * perPage)
        .setMaxResults(perPage)
        .getResultList();
  }
}
