package ru.hh.superscoring.service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Random;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.superscoring.dao.AuthDao;
import ru.hh.superscoring.dao.UserDao;
import ru.hh.superscoring.entity.Token;
import ru.hh.superscoring.entity.User;
import ru.hh.superscoring.util.Hasher;
import ru.hh.superscoring.util.Role;

public class AuthService {

  private final AuthDao authDao;
  private final UserDao userDao;

  public AuthService(AuthDao authDao, UserDao userDao) {
    this.authDao = authDao;
    this.userDao = userDao;
  }

  @Transactional(readOnly = true)
  public String getUserNameByToken(String token) {
    return authDao.getUserNameByToken(token);
  }

  @Transactional(readOnly = true)
  public Integer checkAuthentication(String login, String password) {
    return authDao.getUserIdByLoginAndPassword(login, Hasher.hash(password));
  }

  @Transactional(readOnly = true)
  public Token generateAccessToken(Integer userId) {
    Random random = new SecureRandom();
    String accessToken = new BigInteger(100, random).toString(32);
    Token token = new Token();
    token.setUserId(userId);
    token.setToken(accessToken);
    token.setExpireDate(LocalDateTime.now().plusDays(14));
    authDao.save(token);
    return token;
  }

  @Transactional(readOnly = true)
  public Integer getUserIdByToken(String token) {
    return authDao.getUserIdByToken(token);
  }

  @Transactional(readOnly = true)
  public boolean isAdmin(Integer userId) {
    return (authDao.getUserRoleById(userId) == Role.ADMIN);
  }

  @Transactional(readOnly = true)
  public Integer getUserIdByLogin(String login) {
    return authDao.getUserIdByLogin(login);
  }

  @Transactional(readOnly = true)
  public void addNewUser(String login, String password, String name) {
    User user = new User();
    user.setLogin(login);
    user.setPassword(Hasher.hash(password));
    user.setName(name);
    user.setRole(Role.USER);
    authDao.save(user);
  }

  @Transactional(readOnly = true)
  public Role getRoleByToken(String token) {
    return authDao.getUserRoleByToken(token);
  }

  @Transactional
  public void setAdmin(Integer userId) {
    User user = userDao.get(User.class, userId);
    user.setRole(Role.ADMIN);
    userDao.save(user);
  }

}
