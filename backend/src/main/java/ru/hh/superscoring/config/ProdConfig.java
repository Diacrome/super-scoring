package ru.hh.superscoring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.hh.nab.hibernate.NabSessionFactoryBean;
import ru.hh.nab.starter.NabProdConfig;
import org.springframework.context.annotation.Bean;
import ru.hh.nab.common.properties.FileSettings;
import ru.hh.nab.datasource.DataSourceFactory;
import ru.hh.nab.hibernate.NabHibernateProdConfig;
import javax.sql.DataSource;

@Configuration
@Import({
    NabHibernateProdConfig.class,
    NabProdConfig.class,
    FreeMarkerConfiguration.class,
    CommonConfig.class})
public class ProdConfig {

  @Bean
  public DataSource dataSource(DataSourceFactory dataSourceFactory, FileSettings fileSettings) {
    return dataSourceFactory.create("master", false, fileSettings);
  }

}
