package ru.hh.superscoring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.hh.nab.starter.NabCommonConfig;
import ru.hh.superscoring.dao.AnswerDao;
import ru.hh.superscoring.entity.Answer;
import ru.hh.superscoring.dao.AuthDao;
import ru.hh.superscoring.dao.TestDao;
import ru.hh.superscoring.dao.UserDao;
import ru.hh.superscoring.entity.Test;
import ru.hh.superscoring.resource.AnswerResource;
import ru.hh.superscoring.entity.Token;
import ru.hh.superscoring.entity.User;
import ru.hh.superscoring.resource.AuthResource;
import ru.hh.superscoring.entity.TestPassQuestion;
import ru.hh.superscoring.resource.HelloResource;
import ru.hh.superscoring.resource.QuestionResource;
import ru.hh.superscoring.resource.TestResource;
import ru.hh.superscoring.dao.QuestionDao;
import ru.hh.superscoring.dao.TestPassDao;
import ru.hh.superscoring.entity.Question;
import ru.hh.superscoring.entity.TestPass;
import org.springframework.context.annotation.Bean;
import ru.hh.nab.hibernate.MappingConfig;
import ru.hh.superscoring.service.AnswerService;
import ru.hh.superscoring.resource.TestPassResource;
import ru.hh.superscoring.service.AuthService;
import ru.hh.superscoring.service.QuestionService;
import ru.hh.superscoring.service.StatusService;
import ru.hh.superscoring.service.TestPassService;
import ru.hh.superscoring.service.TestService;


@Configuration
@Import({
    // import your beans here
    QuestionResource.class,
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
    AuthDao.class,
    UserDao.class,
    User.class,
    Token.class,
    AuthService.class,
    AuthResource.class,
    NabCommonConfig.class,
    StatusService.class
})

public class CommonConfig {

  // ENTITES КЛАССЫ УКАЗЫВАТЬ В КОНСТРУКТОРЕ ЧЕРЕЗ ЗАПЯТУЮ
  @Bean
  public MappingConfig mappingConfig() {
    return new MappingConfig(Question.class,
        TestPass.class,
        Test.class,
        User.class,
        Token.class,
        Answer.class,
        TestPassQuestion.class);
  }

}
