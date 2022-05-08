package ru.hh.superscoring.service;

import ru.hh.superscoring.dao.GenericDao;
import ru.hh.superscoring.dto.UserDto;
import ru.hh.superscoring.entity.User;


public class UserService {

  private final GenericDao userDao;

  public UserService(GenericDao userDao) {
    this.userDao = userDao;
  }

  public UserDto getUserById(Integer id) {
    return UserDto.map(userDao.get(User.class, id));
  }

}
