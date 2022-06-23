import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import ru.hh.superscoring.service.AnswerService;
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
  public void verifyAnswerCheckTrueTest() {
    String rightSingleAnswer = "{\"answer\": \"2\"}";
    String rightMultipleAnswer = "{\"multiple_answer1\": \"2\", \"multiple_answer2\": \"4\"}";
    String rightMultipleFillAnswer = "{\"answer1\": \"2\", \"answer2\" : \"3\"}";
    String rightRankingAnswer = "{\"1\": \"4\", \"2\": \"3\", \"3\": \"2\", \"4\": \"1\"}";
    String givenSingleAnswer = "{\"answer\": \"2\"}";
    List<String> givenMultipleAnswers = List.of("{\"multiple_answer1\": \"2\", \"multiple_answer2\": \"4\"}",
        "{\"multiple_answer2\": \"4\", \"multiple_answer1\": \"2\"}");
    List<String> givenFillAnswers = List.of("{\"answer1\": \"2\", \"answer2\" : \"3\"}",
        "{\"answer2\": \"3\", \"answer1\" : \"2\"}");
    List<String> givenRankingAnswers = List.of("{\"1\": \"4\", \"2\": \"3\", \"3\": \"2\", \"4\": \"1\"}",
        "{\"2\": \"3\", \"3\": \"2\", \"1\": \"4\", \"4\": \"1\"}");
    assertTrue(AnswerService.CheckAnswer(rightSingleAnswer, givenSingleAnswer));
    for (String givenMultipleAnswer : givenMultipleAnswers) {
      assertTrue(AnswerService.CheckAnswer(rightMultipleAnswer, givenMultipleAnswer));
    }
    for (String givenMultipleFillAnswer : givenFillAnswers) {
      assertTrue(AnswerService.CheckAnswer(rightMultipleFillAnswer, givenMultipleFillAnswer));
    }
    for (String givenRankingAnswer : givenRankingAnswers) {
      assertTrue(AnswerService.CheckAnswer(rightRankingAnswer, givenRankingAnswer));
    }
  }

  @Test
  public void verifyAnswerCheckFalseTest() {
    String rightSingleAnswer = "{\"answer\": \"2\"}";
    String rightMultipleAnswer = "{\"multiple_answer1\": \"2\", \"multiple_answer2\": \"4\"}";
    String rightMultipleFillAnswer = "{\"answer1\": \"2\", \"answer2\" : \"3\"}";
    String rightRankingAnswer = "{\"1\": \"4\", \"2\": \"3\", \"3\": \"2\", \"4\": \"1\"}";
    List<String> givenSingleAnswers = List.of("{\"answer\": \"3\"}", rightMultipleAnswer);
    List<String> givenMultipleAnswers = List.of("{\"multiple_answer1\": \"2\", \"multiple_answer2\": \"5\"}",
        "{\"multiple_answer1\": \"2\", \"multiple_answer3\": \"4\"}", rightSingleAnswer, rightMultipleFillAnswer);
    List<String> givenFillAnswers = List.of("{\"answer1\": \"3\", \"answer2\" : \"3\"}",
        "{\"answer1\": \"2\", \"answer2\" : \"3\", \"answer3\" : \"4\"}", rightMultipleAnswer, rightRankingAnswer);
    List<String> givenRankingAnswers = List.of("{\"1\": \"3\", \"2\": \"4\", \"3\": \"2\", \"4\": \"1\"}",
        "{\"2\": \"3\", \"3\": \"2\", \"1\": \"4\"}", rightMultipleAnswer, rightMultipleFillAnswer);
    for (String givenSingleAnswer : givenSingleAnswers) {
      assertFalse(AnswerService.CheckAnswer(rightSingleAnswer, givenSingleAnswer));
    }
    for (String givenMultipleAnswer : givenMultipleAnswers) {
      assertFalse(AnswerService.CheckAnswer(rightMultipleAnswer, givenMultipleAnswer));
    }
    for (String givenMultipleFillAnswer : givenFillAnswers) {
      assertFalse(AnswerService.CheckAnswer(rightMultipleFillAnswer, givenMultipleFillAnswer));
    }
    for (String givenRankingAnswer : givenRankingAnswers) {
      assertFalse(AnswerService.CheckAnswer(rightRankingAnswer, givenRankingAnswer));
    }
  }
}
