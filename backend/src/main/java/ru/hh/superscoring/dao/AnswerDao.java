package ru.hh.superscoring.dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.superscoring.entity.Answer;

public class AnswerDao extends GenericDao {

  protected AnswerDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public List<Answer> getListAnswerByTestPassId(Integer testPassId) {
    return new ArrayList<>(getSession()
        .createQuery("select a from Answer a where a.testPass = :test_pass_id order by a.question", Answer.class)
        .setParameter("test_pass_id", testPassId)
        .getResultList());
  }
}
