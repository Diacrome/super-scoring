package ru.hh.super_scoring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.hh.nab.starter.NabProdConfig;

@Configuration
@Import({
         //NabHibernateProdConfig.class,
         NabProdConfig.class,
         CommonConfig.class})
public class ProdConfig {

  /*@Bean
  public DataSource dataSource(DataSourceFactory dataSourceFactory, FileSettings fileSettings) {
    return dataSourceFactory.create("master", false, fileSettings);
  }*/
}
