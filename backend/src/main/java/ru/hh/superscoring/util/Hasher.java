package ru.hh.superscoring.util;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public final class Hasher {

  private static final int ITERATIONS = 1024;  // от 1 до  1073741824 увеличиваем с возрастанием производительности CPU

  private static final String ALGORITHM = "PBKDF2WithHmacSHA1";  // предпочтительный медленный алгоритм хеширования

  private static final String SALT = "gfghadashshsdgas";

  private static final Integer VALUE_BIT_IN_BYTE = 8;

  public static boolean checkPassword(String password, String passwordHash) {
    return passwordHash.equals(hash(password));
  }

  public static String hash(String password) {
    KeySpec specification = new PBEKeySpec(password.toCharArray(),
        SALT.getBytes(StandardCharsets.UTF_8),
        ITERATIONS,
        SALT.length() * VALUE_BIT_IN_BYTE);
    Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding(); // превращаем байтовый хеш в чистую строку
    try {                                                             // для исключения трактовки управляющих символов
      SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(ALGORITHM);
      return encoder.encodeToString(secretKeyFactory.generateSecret(specification).getEncoded());
    } catch (NoSuchAlgorithmException exception) {
      throw new IllegalStateException("Missing algorithm: " + ALGORITHM, exception);
    } catch (InvalidKeySpecException exception) {
      throw new IllegalStateException("Invalid SecretKeyFactory", exception);
    }
  }
}
