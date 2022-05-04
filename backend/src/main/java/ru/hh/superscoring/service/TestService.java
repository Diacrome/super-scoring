package ru.hh.superscoring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.superscoring.dao.GenericDao;
import ru.hh.superscoring.entity.Test;

public class TestService {

  private static final Logger logger = LoggerFactory.getLogger(TestService.class);
  private final GenericDao genericDao;

  public TestService(GenericDao genericDao) {
    this.genericDao = genericDao;
  }

  public Test getTestById(Integer id) {
    return genericDao.get(Test.class, id);
  }
}
