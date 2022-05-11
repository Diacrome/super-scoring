package ru.hh.superscoring.service;

import ru.hh.superscoring.dao.UserDao;

public class UserService {

  private final UserDao userDao;
  private final AuthService authService;

  public UserService(UserDao userDao, AuthService authService) {
    this.userDao = userDao;
    this.authService = authService;
  }

  public String getUserNameByToken(String token) {
    return userDao.getUserNameById(authService.getUserWithToken(token));
  }

}
