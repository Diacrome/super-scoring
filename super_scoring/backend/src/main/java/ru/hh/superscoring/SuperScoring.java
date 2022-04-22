package ru.hh.superscoring;

import ru.hh.nab.common.properties.PropertiesUtils;
import ru.hh.nab.starter.NabApplication;
import ru.hh.superscoring.config.ProdConfig;

public class SuperScoring {

  public static void main(String[] args) {

    NabApplication
            .builder()
            .configureJersey()
            .bindToRoot()
            .build()
            .run(ProdConfig.class);
  }
}
