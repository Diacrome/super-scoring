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
  public String getTrueAnswerOnQuestion(Integer questionId) {
    return getSession()
        .createQuery("select q.answer from Question q where q.id = :question_id", String.class)
        .setParameter("question_id",questionId)
        .uniqueResult();
  }
}
