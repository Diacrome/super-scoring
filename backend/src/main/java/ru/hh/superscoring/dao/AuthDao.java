package ru.hh.superscoring.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

@Qualifier("authDao")
public class AuthDao extends GenericDao {

  protected AuthDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Transactional
  public Integer getUserIdWithToken(String receivedToken) {
    return getSession()
        .createQuery("select t.user_id from Token t where t.receivedToken = :access_token", Integer.class)
        .setParameter("access_token", receivedToken)
        .getSingleResult();
  }

}
