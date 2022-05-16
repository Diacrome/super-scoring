package ru.hh.superscoring.dao;

import org.hibernate.SessionFactory;

public class AnswerDao extends GenericDao {

  protected AnswerDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
