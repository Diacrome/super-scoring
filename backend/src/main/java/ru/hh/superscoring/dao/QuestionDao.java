package ru.hh.superscoring.dao;

import java.util.List;
import jnr.ffi.annotations.In;
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

  @Transactional(readOnly = true)
  public List<Question> getListQuestionByTestPassId(List<Integer> listQuestionId) {
    return getSession()
        .createQuery("select q from Question q where q.id in (:ids)", Question.class)
        .setParameterList("ids", listQuestionId)
        .getResultList();
  }
}
