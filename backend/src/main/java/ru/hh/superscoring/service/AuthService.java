package ru.hh.superscoring.service;

import ru.hh.superscoring.dao.AuthDao;
import java.util.Random;
import java.security.SecureRandom;
import java.math.BigInteger;

public class AuthService {

  private final AuthDao authDao;

  public AuthService(AuthDao authDao) {
    this.authDao = authDao;
  }

  public Integer getUserWithToken(String accessToken) {
    return authDao.getUserIdWithToken(accessToken);
  }

  public Integer checkAuthentification(String login, String password) {
    return authDao.findUser(login, password);
  }

  public void generateAccessToken(Integer userId) {
    Random random = new SecureRandom();
    String token = new BigInteger(100, random).toString(32);
    authDao.generateNewAccessToken(userId, token);
  }

}
