package ru.hh.superscoring.dao;

import org.hibernate.SessionFactory;
import ru.hh.superscoring.entity.Test;

public class TestDao extends GenericDao {


  public TestDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public Test getTestById(Integer id) {
    return get(Test.class, id);
  }

}
