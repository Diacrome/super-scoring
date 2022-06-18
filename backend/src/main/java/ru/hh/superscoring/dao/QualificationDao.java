package ru.hh.superscoring.dao;

import java.util.List;
import org.hibernate.SessionFactory;
import ru.hh.superscoring.entity.Qualification;


public class QualificationDao extends GenericDao {

  protected QualificationDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public List<String> getTestQualification(Integer testId) {
    return getSession()
        .createQuery("select q.qualificationName from Qualification q where q.testId = :id order by q.orderNumber", String.class)
        .setParameter("id", testId)
        .getResultList();
  }
}