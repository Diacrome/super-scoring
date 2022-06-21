package ru.hh.superscoring.dao;

import org.hibernate.SessionFactory;
import ru.hh.superscoring.util.Role;

public class AuthDao extends GenericDao {

  protected AuthDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public String getUserNameByToken(String token) {
    return getSession()
        .createQuery("select u.name from User u join Token t on u.id = t.userId where t.token = :token and t.expireDate > now()", String.class)
        .setParameter("token", token)
        .getSingleResult();
  }

  public Integer getUserIdByLoginAndPassword(String login, String password) {
    return getSession()
        .createQuery("select u.id from User u where u.login = :login and u.password = :password", Integer.class)
        .setParameter("login", login)
        .setParameter("password", password)
        .getSingleResult();
  }

  public Integer getUserIdByToken(String token) {
    return getSession()
        .createQuery("select userId from Token where token = :token", Integer.class)
        .setParameter("token", token)
        .uniqueResult();
  }
  //ToDo: используется для получения id в одном случае и для проверки есть ли пользователь в другом, лучше сделать доп. метод checkUserExists, который будет возвращать true/false
  public Integer getUserIdByLogin(String login) {
    return getSession()
        .createQuery("select u.id from User u where u.login = :login", Integer.class)
        .setParameter("login", login)
        .uniqueResult();
  }

  public Role getUserRoleById(Integer userId){
    return getSession()
        .createQuery("select role from User where id = :userId", Role.class)
        .setParameter("userId", userId)
        .uniqueResult();
  }

  public Role getUserRoleByToken(String token) {
    return getSession()
        .createQuery("select u.role from User u where u.id = (select t.userId from Token t where t.token = :token)", Role.class)
        .setParameter("token",token)
        .uniqueResult();
  }

}
