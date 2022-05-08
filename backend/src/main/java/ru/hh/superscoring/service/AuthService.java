package ru.hh.superscoring.service;

import java.time.LocalDateTime;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.superscoring.dao.AuthDao;
import java.util.Random;
import java.security.SecureRandom;
import java.math.BigInteger;
import ru.hh.superscoring.entity.Token;

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
    return authDao.findUser(login, password);
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

}
