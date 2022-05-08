package ru.hh.superscoring.service;

import ru.hh.superscoring.dao.AuthDao;

public class AuthService {

  private final AuthDao authDao;

  public AuthService(AuthDao authDao) {
    this.authDao = authDao;
  }

  public Integer getUserWithToken(String token) {
    return authDao.getUserIdWithToken(token);
  }
}
