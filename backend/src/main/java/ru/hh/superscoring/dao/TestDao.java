package ru.hh.superscoring.dao;

import org.hibernate.SessionFactory;

public class TestDao extends GenericDao {

  protected TestDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }
}
