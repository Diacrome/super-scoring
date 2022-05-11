package ru.hh.superscoring.dao;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

public class AuthDao extends GenericDao {

  protected AuthDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Transactional(readOnly = true)
  public Integer getUserIdWithToken(String token) {
    return getSession()
        .createQuery("select t.userId from Token t where t.token = :token", Integer.class)
        .setParameter("token", token)
        .getSingleResult();
  }

  @Transactional(readOnly = true)
  public Integer findUser (String login, String password) {
    return getSession()
        .createQuery("select u.id from User u where u.login = :login and u.password = :password", Integer.class)
        .setParameter("login", login)
        .setParameter("password", password)
        .getSingleResult();
  }

}
