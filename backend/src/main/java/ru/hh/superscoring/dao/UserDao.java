package ru.hh.superscoring.dao;

import javax.transaction.Transactional;
import org.hibernate.SessionFactory;

public class UserDao extends GenericDao {

  protected UserDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Transactional
  public String getUserNameById(Integer userId) {
    return getSession()
        .createQuery("select u.name from User u where u.id =:id", String.class)
        .setParameter("id", userId)
        .getSingleResult();
  }

}

