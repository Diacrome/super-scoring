package ru.hh.superscoring.service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Random;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.superscoring.dao.AuthDao;
import ru.hh.superscoring.entity.Token;
import ru.hh.superscoring.entity.User;
import ru.hh.superscoring.util.Hasher;
import ru.hh.superscoring.util.Role;

public class AuthService {

  private final AuthDao authDao;

  public AuthService(AuthDao authDao) {
    this.authDao = authDao;
  }

  @Transactional(readOnly = true)
  public String getUserWithToken(String token) {
    return authDao.getUserNameWithToken(token);
  }

  @Transactional(readOnly = true)
  public Integer checkAuthentication(String login, String password) {
    return authDao.findUser(login, Hasher.hash(password));
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
  public Integer getUserIdWithToken(String token) {
    return authDao.getUserIdWithToken(token);
  }

  @Transactional(readOnly = true)
  public boolean isAdmin(Integer userId) {
    return (authDao.getRoleById(userId) == Role.ADMIN);
  }

  @Transactional(readOnly = true)
  public Integer checkAuthenticationByLogin(String login) {
    return authDao.findUserByLogin(login);
  }

  @Transactional(readOnly = true)
  public void addUser(String login, String password, String name, Role role) {
    User user = new User();
    user.setLogin(login);
    user.setPassword(Hasher.hash(password));
    user.setName(name);
    user.setRole(role);
    authDao.save(user);
  }

  @Transactional(readOnly = true)
  public Role getRoleByToken(String token) {
    return authDao.getRoleUserByTokenFromDataBase(token);
  }

}
