package ru.hh.superscoring.dao;
import org.hibernate.SessionFactory;

public class UserDao extends GenericDao {

  protected UserDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
