package ru.hh.superscoring.dao;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

public class AuthDao extends GenericDao {

  protected AuthDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Transactional(readOnly = true)
  public String getUserNameWithToken(String token) {
    return getSession()
        .createQuery("select u.name from User u join Token t on u.id = t.userId where t.token = :token and t.expireDate > now()", String.class)
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
