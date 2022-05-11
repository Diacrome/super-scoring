package ru.hh.superscoring.dao;

import javax.transaction.Transactional;
import org.hibernate.SessionFactory;

public class UserDao extends GenericDao {

  protected UserDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
