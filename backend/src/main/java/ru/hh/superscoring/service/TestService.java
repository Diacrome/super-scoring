package ru.hh.superscoring.service;

import ru.hh.superscoring.dao.GenericDao;
import ru.hh.superscoring.dao.TestDao;
import ru.hh.superscoring.dto.TestDto;
import ru.hh.superscoring.entity.Test;

public class TestService {

  private final TestDao testDao;

  public TestService(TestDao testDao) {
    this.testDao = testDao;
  }

  public TestDto getTestById(Integer id) {
    return TestDto.map(testDao.get(Test.class, id));
  }
}
