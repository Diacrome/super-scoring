package ru.hh.superscoring.service;

import ru.hh.superscoring.dao.GenericDao;
import ru.hh.superscoring.dto.TestDto;
import ru.hh.superscoring.entity.Test;

public class TestService {

  private final GenericDao genericDao;

  public TestService(GenericDao genericDao) {
    this.genericDao = genericDao;
  }

  public TestDto getTestById(Integer id) {
    return TestDto.map(genericDao.get(Test.class, id));
  }
}
