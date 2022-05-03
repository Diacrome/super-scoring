package ru.hh.superscoring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.hh.nab.starter.NabCommonConfig;
import ru.hh.superscoring.dao.GenericDao;
import ru.hh.superscoring.dao.TestDao;
import ru.hh.superscoring.entity.Test;
import ru.hh.superscoring.resource.TestResource;
import org.springframework.context.annotation.Bean;
import ru.hh.nab.hibernate.MappingConfig;
import ru.hh.superscoring.service.TestService;

import java.lang.reflect.GenericArrayType;


@Configuration
@Import({
    // import your beans here
    TestDao.class,
    TestResource.class,
    Test.class,
    TestService.class,
    NabCommonConfig.class
})

public class CommonConfig {

  // ENTITES КЛАССЫ УКАЗЫВАТЬ В КОНСТРУКТОРЕ ЧЕРЕЗ ЗАПЯТУЮ
  @Bean
  public MappingConfig mappingConfig() {
    return new MappingConfig(Test.class);
  }

}
