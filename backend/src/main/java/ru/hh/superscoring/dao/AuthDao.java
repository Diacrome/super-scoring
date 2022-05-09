package ru.hh.superscoring.dao;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

public class AuthDao extends GenericDao {

  protected AuthDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Transactional
  public Integer getUserIdWithToken(String accessToken) {
    return getSession()
        .createQuery("select t.userId from Token t where t.accessToken = :access_token", Integer.class)
        .setParameter("access_token", accessToken)
        .getSingleResult();
  }

  @Transactional
  public Integer findUser (String login, String password) {
    return getSession()
        .createQuery("select u.userId from User u where u.login = :login and u.password = :password", Integer.class)
        .setParameter("login", login)
        .setParameter("password", password)
        .getSingleResult();
  }

  // проверить
  public void generateNewAccessToken(Integer userId, String accessToken) {
    getSession()
        .createQuery("insert into Token(userId, accessToken, expireDate)" +
                    "values (:user_id, :access_token, current_time() + 900)")
        .setParameter("user_id", userId)
        .setParameter("access_token", accessToken)
        .executeUpdate();
  }
}
