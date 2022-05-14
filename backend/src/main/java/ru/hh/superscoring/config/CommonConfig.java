package ru.hh.superscoring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.hh.nab.starter.NabCommonConfig;
import ru.hh.superscoring.dao.AnswerDao;
import ru.hh.superscoring.entity.Answer;
import ru.hh.superscoring.dao.TestDao;
import ru.hh.superscoring.entity.Test;
import ru.hh.superscoring.resource.AnswerResource;
import ru.hh.superscoring.resource.HelloResource;
import ru.hh.superscoring.resource.TestResource;
import ru.hh.superscoring.dao.QuestionDao;
import ru.hh.superscoring.dao.TestPassDao;
import ru.hh.superscoring.entity.Question;
import ru.hh.superscoring.entity.TestPass;
import org.springframework.context.annotation.Bean;
import ru.hh.nab.hibernate.MappingConfig;
import ru.hh.superscoring.service.AnswerService;
import ru.hh.superscoring.resource.TestPassResource;
import ru.hh.superscoring.service.QuestionService;
import ru.hh.superscoring.service.TestPassService;
import ru.hh.superscoring.service.TestService;


@Configuration
@Import({
    // import your beans here
    TestService.class,
    AnswerDao.class,
    AnswerService.class,
    AnswerResource.class,
    HelloResource.class,
    TestResource.class,
    Test.class,
    TestDao.class,
    TestService.class,
    QuestionService.class,
    Question.class,
    QuestionDao.class,
    TestPass.class,
    TestPassService.class,
    TestPassDao.class,
    TestPassResource.class,
    NabCommonConfig.class
})

public class CommonConfig {

  // ENTITES КЛАССЫ УКАЗЫВАТЬ В КОНСТРУКТОРЕ ЧЕРЕЗ ЗАПЯТУЮ
  @Bean
  public MappingConfig mappingConfig() {
    return new MappingConfig(Question.class, TestPass.class, Test.class, Answer.class);
  }

}
