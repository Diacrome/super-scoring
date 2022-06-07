import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import ru.hh.superscoring.util.JsonValidator;

public class JsonValidatorTest {


  private static final String multipleChoiceQuestionPayload = "{\"1\": 1, \"2\": 3, \"3\": 4, \"4\": 6}";
  private static final String singleChoiceQuestionPayload = "{\"1\": 1, \"2\": 5, \"3\": 4, \"4\": 1}";
  private static final String multipleQuestionsSingleChoiceQuestionPayload = "{\"answer1\":{\"1\": \"plants\", \"2\": \"is planting\", \"3\": \"has planted\"}, " +
      "\"answer2\": {\"1\": \"plants\", \"2\": \"is planting\", \"3\": \"has planted\"}}";

  @Test
  public void verifyMultipleChoiceTrueTest() throws JsonProcessingException {
    List<String> goodJsons = List.of(
        "{\"multiple_answer1\": \"2\", \"multiple_answer2\": \"4\"}",
        "{\"multiple_answer2\": \"4\", \"multiple_answer1\": \"3\", \"multiple_answer3\": \"1\"}",
        "{\"multiple_answer1\": \"2\"}"
    );
    for (String goodJson : goodJsons) {
      assertTrue(JsonValidator.verifyMultipleChoice(goodJson, multipleChoiceQuestionPayload));
    }
  }

  @Test
  public void verifyMultipleChoiceFalseTest() throws JsonProcessingException {
    List<String> badJsons = List.of(
        "",
        "{}",
        "{\"answer\": \"2\"}",
        "{\"multiple_answer1\": \"8\"}",
        "{\"multiple_answer1\": 2}",
        "{\"multiple_answer1 \"2\", \"multiple_answer2\": \"4\"}"
    );
    for (String badJson : badJsons) {
      assertFalse(JsonValidator.verifyMultipleChoice(badJson, multipleChoiceQuestionPayload));
    }
  }

  @Test
  public void verifySingleChoiceTrueTest() throws JsonProcessingException {
    List<String> goodJsons = List.of(
        "{\"answer\": \"1\"}",
        "{\"answer\": \"3\"}",
        "{\"answer\": \"4\"}"
    );
    for (String goodJson : goodJsons) {
      assertTrue(JsonValidator.verifySingleChoice(goodJson, singleChoiceQuestionPayload));
    }
  }

  @Test
  public void verifySingleChoiceFalseTest() throws JsonProcessingException {
    List<String> badJsons = List.of(
        "",
        "{}",
        "{\"answer1\": \"2\"}",
        "{\"answer\": \"8\"}",
        "{\"answer\": 2}",
        "{\"answer\"= \"2\"}"
    );
    for (String badJson : badJsons) {
      assertFalse(JsonValidator.verifySingleChoice(badJson, singleChoiceQuestionPayload));
    }
  }

  @Test
  public void verifyMultipleQuestionsSingleChoiceTrueTest() throws JsonProcessingException {
    List<String> goodJsons = List.of(
        "{\"answer1\" : \"2\", \"answer2\" : \"3\"}",
        "{\"answer2\":\"2\", \"answer1\":\"1\"}",
        "{ \"answer1\" : \"3\", \"answer2\" : \"1\" }"
    );
    for (String goodJson : goodJsons) {
      assertTrue(JsonValidator.verifyMultipleQuestionsSingleChoice(goodJson, multipleQuestionsSingleChoiceQuestionPayload));
    }
  }

  @Test
  public void verifyMultipleQuestionsSingleChoiceFalseTest() throws JsonProcessingException {
    List<String> badJsons = List.of(
        "",
        "{}",
        "{\"answer1\": \"2\", \"answer44\" : \"3\"}",
        "{\"answer1\": \"33\", \"answer2\" : \"3\"}",
        "{\"answer1\": 2, \"answer2\" : 3}",
        "{\"answer1\": \"2\"",
        "{\"answer1\": \"2\", \"answer2\" : \"3\", \"answer3\" : \"1\"}",
        "{\"answer1\": \"2\" \"answer2\" : \"3\"}"
    );
    for (String badJson : badJsons) {
      assertFalse(JsonValidator.verifyMultipleQuestionsSingleChoice(badJson, multipleQuestionsSingleChoiceQuestionPayload));
    }
  }
}
