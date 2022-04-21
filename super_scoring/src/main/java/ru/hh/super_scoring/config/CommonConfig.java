package ru.hh.super_scoring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.hh.nab.starter.NabCommonConfig;
import ru.hh.super_scoring.resource.ExampleResource;

@Configuration
@Import({
  // import your beans here
  ExampleResource.class,
  NabCommonConfig.class
})
public class CommonConfig {

  /*@Bean
  public MappingConfig mappingConfig() {
    MappingConfig mappingConfig = new MappingConfig();
    mappingConfig.addPackagesToScan("ru.hh.school.entity");
    return mappingConfig;
  }*/
}
