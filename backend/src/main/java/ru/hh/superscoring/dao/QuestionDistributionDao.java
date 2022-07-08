package ru.hh.superscoring.dao;

import java.util.List;
import org.hibernate.SessionFactory;
import ru.hh.superscoring.entity.QuestionDistribution;

public class QuestionDistributionDao extends GenericDao {

  protected QuestionDistributionDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public List<QuestionDistribution> getAllQuestionDistributionsForTest(Integer testId) {
    return getSession()
        .createQuery("select qd from QuestionDistribution qd where qd.testId = :id", QuestionDistribution.class)
        .setParameter("id", testId)
        .getResultList();
  }

  public Integer deleteQuestionDistribution(Integer distributionId) {
    return getSession()
        .createQuery("delete qd from QuestionDistribution qd where qd.id = :distributionId")
        .setParameter("distributionId", distributionId)
        .executeUpdate();
  }
}
