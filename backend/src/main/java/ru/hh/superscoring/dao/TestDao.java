package ru.hh.superscoring.dao;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.superscoring.entity.Test;

import java.util.List;

public class TestDao extends GenericDao {

  protected TestDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }
  //TODO:баг Transactional вроде должен быть в сервисе
  //TODO:баг в месте применения никак не обрабатывается ошибка NoResultException
  @Transactional(readOnly = true)
  public Integer getTestSizeById(Integer testId) {
    return getSession()
        .createQuery("select t.questionQuantity from Test t where t.id = :test_id", Integer.class)
        .setParameter("test_id", testId).getSingleResult();
  }
  //TODO: Нужна ли такая проверка?
  public Boolean isTestActive(Integer testId) {
    return getSession()
        .createQuery("select isActive from Test where id = :test_id", Boolean.class)
        .setParameter("test_id", testId)
        .uniqueResult();
  }
  //TODO:улучшение возможно удобно иметь метод, который возвращает тест, только в том случае если он активен т.к. список всех тестов скорее нужен админу, чем обычному пользователю
  public Test getTestById(Integer testId) {
    return getSession()
        .createQuery("select t from Test t where id = :testId", Test.class)
        .setParameter("testId", testId)
        .uniqueResult();
  }
  //TODO:улучшение возможно можно чуть-чуть проще написать запрос, через join
  public Integer getTestSizeByTestPassId(Integer testPassId) {
    return getSession()
        .createQuery("select t.questionQuantity from Test t where t.id = " +
            "(select tp.testId from TestPass tp where tp.id = :test_pass_id)", Integer.class)
        .setParameter("test_pass_id", testPassId)
        .uniqueResult();
  }
  //TODO:баг метод возвращает все тесты, возможно нужен ещё метод, который будет возвращать только активные
  public List<Test> getAllTests(int page, int perPage) {
    return getSession()
        .createQuery("select t from Test t " +
            "order by t.id asc", Test.class)
        .setFirstResult(page * perPage)
        .setMaxResults(perPage)
        .getResultList();
  }
}
