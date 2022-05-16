package ru.hh.superscoring.dao;

import org.hibernate.SessionFactory;

public class AuthDao extends GenericDao {

  protected AuthDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public String getUserNameWithToken(String token) {
    return getSession()
        .createQuery("select u.name from User u join Token t on u.id = t.userId where t.token = :token and t.expireDate > now()", String.class)
        .setParameter("token", token)
        .getSingleResult();
  }

  public Integer findUser(String login, String password) {
    return getSession()
        .createQuery("select u.id from User u where u.login = :login and u.password = :password", Integer.class)
        .setParameter("login", login)
        .setParameter("password", password)
        .getSingleResult();
  }

  public Integer getUserIdWithToken(String token) {
    return getSession()
        .createQuery("select userId from Token where token = :token", Integer.class)
        .setParameter("token", token)
        .uniqueResult();
  }

}
