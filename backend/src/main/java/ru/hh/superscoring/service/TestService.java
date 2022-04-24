package ru.hh.superscoring.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.hh.superscoring.dao.GenericDao;
import ru.hh.superscoring.entity.Test;


public class TestService {
  private final ObjectMapper mapper;
  private final GenericDao genericDao;


  public TestService(GenericDao genericDao) {
    this.mapper = new ObjectMapper();
    this.genericDao = genericDao;

  }

  public Test getTestById(Integer id) {
    return genericDao.get(Test.class, id);
  }

}
