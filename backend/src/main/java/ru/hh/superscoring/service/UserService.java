package ru.hh.superscoring.service;

import ru.hh.superscoring.dao.UserDao;
import ru.hh.superscoring.dto.UserDto;
import ru.hh.superscoring.entity.User;


public class UserService {

  private final UserDao userDao;

  public UserService(UserDao userDao) {
    this.userDao = userDao;
  }

  public UserDto getUserById(Integer id) {
    return UserDto.map(userDao.get(User.class, id));
  }

}
