package ru.hh.superscoring.service;

import ru.hh.superscoring.dao.GenericDao;
import ru.hh.superscoring.dto.TestDto;
import ru.hh.superscoring.entity.Test;

public class TestService {

  private final GenericDao genericDao;

  public TestService(GenericDao genericDao) {
    this.genericDao = genericDao;
  }

  private TestDto convertTestToTestDto(Test test) {
    if (test != null) {
      TestDto testDto = new TestDto();
      testDto.setId(test.getId());
      testDto.setName(test.getName());
      testDto.setDescription(test.getDescription());
      return testDto;
    } else {
      return null;
    }
  }

  public TestDto getTestById(Integer id) {
    return convertTestToTestDto(genericDao.get(Test.class, id));
  }
}
