import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import ru.hh.superscoring.util.JsonValidator;

public class JsonValidatorTest {


  private static final String multipleChoiceQuestionPayload = "{\"1\": 1, \"2\": 3, \"3\": 4, \"4\": 6}";
  private static final String singleChoiceQuestionPayload = "{\"1\": 1, \"2\": 5, \"3\": 4, \"4\": 1}";
  private static final String multipleQuestionsSingleChoiceQuestionPayload = "{\"answer1\":{\"1\": \"plants\", \"2\": \"is planting\", \"3\": \"has planted\"}, " +
      "\"answer2\": {\"1\": \"plants\", \"2\": \"is planting\", \"3\": \"has planted\"}}";
  private static final String rankingPayload1 = "{\"1\": \"text1\", \"2\": \"text2\", \"3\": \"text3\", \"4\": \"text4\", \"5\": \"text5\"}";
  private static final String rankingPayload2 = "{\"1\": \"text1\", \"2\": \"text2\", \"3\": \"text3\", \"4\": \"text4\"}";
  private static final String rankingPayload3 = "{\"1\": \"text1\", \"2\": \"text2\", \"3\": \"text3\"}";

  @Test
  public void verifyRankingTrueTest() throws JsonProcessingException {

    String goodJson = "{\"1\": \"2\", \"2\": \"4\",\"3\": \"3\", \"4\": \"1\", \"5\": \"5\"}";
    assertTrue(JsonValidator.verifyRanking(goodJson, rankingPayload1));

    goodJson = "{\"1\": \"2\", \"2\": \"4\",\"3\": \"3\", \"4\": \"1\"}";
    assertTrue(JsonValidator.verifyRanking(goodJson, rankingPayload2));

    goodJson = "{\"1\": \"2\", \"2\": \"1\",\"3\": \"3\"}";
    assertTrue(JsonValidator.verifyRanking(goodJson, rankingPayload3));
  }

  @Test
  public void verifyRankingFalseTest() throws JsonProcessingException {
    List<String> badJsons = List.of(
        "",
        "{}",
        "{\"1\": \"2\",\"2\": \"1\", \"3\" \"3\"}",
        "{\"1\": \"2\",\"2\": \"1\", \"3\": \"1\"}",
        "{\"1\": \"2\",\"2\": \"1\", \"3\": \"3\", \"4\": \"4\"},",
        "{\"1\": \"2\",\"2\": \"1\" \"3\": \"3\"}"
    );
    for (String badJson : badJsons) {
      assertFalse(JsonValidator.verifyRanking(badJson, rankingPayload3));
    }
  }

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

  @Test
  public void verifySinglePayloadTrueTest() throws JsonProcessingException {
    List<String> goodPayloads = List.of(
        multipleChoiceQuestionPayload,
        singleChoiceQuestionPayload,
        rankingPayload1,
        rankingPayload2,
        rankingPayload3);
    for (String payload : goodPayloads) {
      assertTrue(payload, JsonValidator.verifySinglePayload(payload));
    }
  }

  @Test
  public void verifySinglePayloadFalseTest() throws JsonProcessingException {
    List<String> goodPayloads = List.of(
        multipleQuestionsSingleChoiceQuestionPayload,
        "{\"2\": 3, \"3\": 4, \"4\": 6}",
        "{\"1\": 1, \"2\": 3, \"4\": 6}",
        "111111");
    for (String payload : goodPayloads) {
      assertFalse(payload, JsonValidator.verifySinglePayload(payload));
    }
  }

  @Test
  public void verifyMultiplePayloadTrueTest() throws JsonProcessingException {
    List<String> goodPayloads = List.of(
        multipleQuestionsSingleChoiceQuestionPayload,
        "{\"answer1\":{\"1\": \"1\", \"2\": \"2\"},\"answer2\": {\"1\": \"1\", \"2\": \"2\", \"3\": \"3\"}}");
    for (String goodPayload : goodPayloads) {
      assertTrue(JsonValidator.verifyMultiplePayload(goodPayload));
    }
  }

  @Test
  public void verifyMultiplePayloadFalseTest() throws JsonProcessingException {
    List<String> badPayloads = List.of(
        multipleChoiceQuestionPayload,
        singleChoiceQuestionPayload,
        rankingPayload1,
        rankingPayload2,
        rankingPayload3);
    for (String badPayload : badPayloads) {
      assertFalse(JsonValidator.verifyMultipleQuestionsSingleChoice(badPayload, multipleQuestionsSingleChoiceQuestionPayload));
    }
  }
}
