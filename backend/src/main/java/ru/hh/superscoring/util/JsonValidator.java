package ru.hh.superscoring.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonValidator {
  private static final ObjectMapper mapper = new ObjectMapper();

  public static boolean verifySingleChoice(String jsonString, String questionPayload) throws JsonProcessingException {
    HashMap payloadMap = mapper.readValue(questionPayload, HashMap.class);
    try {
      JsonNode root = mapper.readTree(jsonString);
      if (root.isEmpty()) {
        return false;
      }
      Iterator<String> fieldNames = root.fieldNames();
      return (fieldNames.next().equals("answer")
          && !fieldNames.hasNext()
          && root.path("answer").isTextual()
          && payloadMap.containsKey(root.path("answer").asText()));
    } catch (JsonProcessingException e) {
      return false;
    }
  }

  public static boolean verifyMultipleChoice(String jsonString, String questionPayload) throws JsonProcessingException {
    HashMap payloadMap = mapper.readValue(questionPayload, HashMap.class);
    Pattern pattern = Pattern.compile("^multiple_answer[0-9]+$");
    try {
      JsonNode root = mapper.readTree(jsonString);
      if (root.isEmpty()) {
        return false;
      }
      for (Iterator<String> fieldNames = root.fieldNames(); fieldNames.hasNext(); ) {
        String fieldName = fieldNames.next();
        Matcher matcher = pattern.matcher(fieldName);
        if (!matcher.matches()
            || !payloadMap.containsKey(root.path(fieldName).asText())
            || !root.path(fieldName).isTextual()) {
          return false;
        }
      }
      return true;
    } catch (
        JsonProcessingException e) {
      return false;
    }
  }

  public static boolean verifyRanking(String jsonString, String questionPayload) throws JsonProcessingException {
    try {
      HashMap payloadMap = mapper.readValue(questionPayload, HashMap.class);
      JsonNode root = mapper.readTree(jsonString);
      if (root.size() != payloadMap.size()) {
        return false;
      }
      Set<String> valuesOfJson = new HashSet<>();
      for (Iterator<String> fieldNames = root.fieldNames(); fieldNames.hasNext(); ) {
        String fieldName = fieldNames.next();
        String valueOfJson = root.path(fieldName).asText();
        if (!payloadMap.containsKey(valueOfJson)
            || !root.path(fieldName).isTextual()) {
          return false;
        }
        valuesOfJson.add(valueOfJson);
      }
      if (!valuesOfJson.equals(payloadMap.keySet())) {
        return false;
      }
      return true;
    } catch (JsonProcessingException e) {
      return false;
    }
  }

  public static boolean verifyMultipleQuestionsSingleChoice(String jsonString, String questionPayload) throws JsonProcessingException {
    HashMap<String, HashMap<String, String>> payloadMap = mapper.readValue(questionPayload, HashMap.class);
    Set<String> payloadKeys = payloadMap.keySet();
    try {
      if (mapper.readValue(jsonString, HashMap.class).size() != payloadMap.size()) {
        return false;
      }

      JsonNode root = mapper.readTree(jsonString);
      for (String key : payloadKeys) {
        if (!root.path(key).isTextual() || !payloadMap.get(key).containsKey(root.path(key).asText())) {
          return false;
        }
      }

      return true;

    } catch (JsonProcessingException e) {
      return false;
    }
  }

  public static boolean verifyAnswer(String answer, String payload, QuestionAnswerType answerType) throws JsonProcessingException {

    if (answerType == QuestionAnswerType.SINGLE_CHOICE) {
      return JsonValidator.verifySingleChoice(answer, payload);
    }
    if (answerType == QuestionAnswerType.MULTIPLE_CHOICE) {
      return JsonValidator.verifyMultipleChoice(answer, payload);
    }
    if (answerType == QuestionAnswerType.MULTIPLE_QUESTIONS_SINGLE_CHOICE) {
      return JsonValidator.verifyMultipleQuestionsSingleChoice(answer, payload);
    }
    if (answerType == QuestionAnswerType.RANKING) {
      return JsonValidator.verifyRanking(answer, payload);
    }
    return false;
  }

  public static boolean verifySinglePayload(String payload) {
    try {
      JsonNode root = mapper.readTree(payload);
      if (root.isEmpty()) {
        return false;
      }
      Iterator<String> fieldNames = root.fieldNames();
      Integer currentVariant = 1;
      while (fieldNames.hasNext()) {
        if (fieldNames.next().equals(currentVariant.toString())) {
          return false;
        }
        currentVariant++;
      }
    } catch (JsonProcessingException e) {
      return false;
    }
    return true;
  }

  public static boolean verifyMultiplePayload(String payload) {
    try {
      JsonNode root = mapper.readTree(payload);
      if (root.isEmpty()) {
        return false;
      }
      Iterator<String> fieldNames = root.fieldNames();
      while (fieldNames.hasNext()) {
        String currentAnswer = fieldNames.next();
        if (!currentAnswer.startsWith("answer")) {
          return false;
        }
        if (!JsonValidator.verifySinglePayload(root.path(currentAnswer).asText())) {
          return false;
        }
      }
    } catch (JsonProcessingException e) {
      return false;
    }
    return true;
  }

  public static boolean verifyPayload(String payload, QuestionAnswerType answerType) {
    if (answerType == QuestionAnswerType.SINGLE_CHOICE || answerType == QuestionAnswerType.MULTIPLE_CHOICE) {
      return JsonValidator.verifySinglePayload(payload);
    }
    if (answerType == QuestionAnswerType.MULTIPLE_QUESTIONS_SINGLE_CHOICE) {
      return JsonValidator.verifyMultiplePayload(payload);
    }
    return false;
  }


}
