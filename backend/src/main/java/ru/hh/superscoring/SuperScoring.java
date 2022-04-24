package ru.hh.superscoring;

import ru.hh.nab.common.properties.PropertiesUtils;
import ru.hh.nab.starter.NabApplication;
import ru.hh.superscoring.config.ProdConfig;

public class SuperScoring {

  public static void main(String[] args) {
    System.setProperty(PropertiesUtils.SETINGS_DIR_PROPERTY, "src/etc");

    NabApplication
        .builder()
        .configureJersey()
        .bindToRoot()
        .build()
        .run(ProdConfig.class);
  }
}
