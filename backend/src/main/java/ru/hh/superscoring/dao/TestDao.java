package ru.hh.superscoring.dao;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.superscoring.dto.TestDto;
import ru.hh.superscoring.entity.Test;

import java.util.List;

public class TestDao extends GenericDao {

  protected TestDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Transactional(readOnly = true)
  public Integer getTestSize(Integer testId) {
    return getSession()
        .createQuery("select t.questionQuantity from Test t where t.id = :test_id", Integer.class)
        .setParameter("test_id", testId).getSingleResult();
  }

  public Boolean isExistActiveTest(Integer testId) {
    return getSession()
        .createQuery("select isActive from Test where id = :test_id", Boolean.class)
        .setParameter("test_id", testId)
        .uniqueResult();
  }

  public Test getTestById(Integer testId) {
    return getSession()
        .createQuery("select t from Test t where id = :testId", Test.class)
        .setParameter("testId", testId)
        .uniqueResult();
  }

  public Integer getTestSizeByTestPassId(Integer testPassId) {
    return getSession()
        .createQuery("select t.questionQuantity from Test t where t.id = " +
            "(select tp.testId from TestPass tp where tp.id = :test_pass_id)", Integer.class)
        .setParameter("test_pass_id", testPassId)
        .uniqueResult();
  }

  public List<Test> getAllTests(int page, int perPage) {
    return getSession()
        .createQuery("select t from Test t " +
            "order by t.id asc", Test.class)
        .setFirstResult(page * perPage)
        .setMaxResults(perPage)
        .getResultList();
  }

  public List<TestDto> getAllTestsForUser(int page, int perPage) {
    return getSession()
        .createQuery("select new ru.hh.superscoring.dto.TestDto(t.id,t.name,t.description) from Test t " +
            "where t.isActive = true order by t.id asc", TestDto.class)
        .setFirstResult(page * perPage)
        .setMaxResults(perPage)
        .getResultList();
  }

}
