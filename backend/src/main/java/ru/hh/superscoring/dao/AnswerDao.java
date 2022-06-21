package ru.hh.superscoring.dao;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.superscoring.entity.Answer;

import java.util.ArrayList;
import java.util.List;

public class AnswerDao extends GenericDao {

  protected AnswerDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public List<Integer> getAnsweredQuestionOrderList(int testPassId) {
    String sqlQuery = "SELECT questionOrder " +
        "FROM Answer                   " +
        "WHERE testPass = :id ";
    return getSession()
        .createQuery(sqlQuery, Integer.class)
        .setParameter("id", testPassId)
        .getResultList();
  }

  public List<Answer> getAnswerListByTestPassId(Integer testPassId) {
    return new ArrayList<>(getSession()
        .createQuery("select a from Answer a where a.testPass = :test_pass_id order by a.questionOrder", Answer.class)
        .setParameter("test_pass_id", testPassId)
        .getResultList());
  }
}
