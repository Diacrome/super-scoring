package ru.hh.superscoring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.hh.nab.starter.NabCommonConfig;
import ru.hh.superscoring.dao.AnswerDao;
import ru.hh.superscoring.dao.GenericDao;
import ru.hh.superscoring.entity.Answer;
import ru.hh.superscoring.entity.Test;
import ru.hh.superscoring.resource.AnswerResource;
import ru.hh.superscoring.resource.ExampleResource;
import org.springframework.context.annotation.Bean;
import ru.hh.nab.hibernate.MappingConfig;
import ru.hh.superscoring.service.AnswerService;
import ru.hh.superscoring.service.TestService;


@Configuration
@Import({
    // import your beans here
    ExampleResource.class,
    TestService.class,
    GenericDao.class,
    AnswerService.class,
    AnswerResource.class,
    NabCommonConfig.class
})
public class CommonConfig {

  @Bean
  public MappingConfig mappingConfig() {
    return new MappingConfig(Test.class, Answer.class);
  }
}
