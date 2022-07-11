package ru.hh.superscoring.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import ru.hh.superscoring.dto.Model;
import ru.hh.superscoring.entity.Question;
import ru.hh.superscoring.service.AuthService;
import ru.hh.superscoring.service.QuestionService;
import ru.hh.superscoring.service.TestService;
import ru.hh.superscoring.util.QuestionAnswerType;

import javax.persistence.GeneratedValue;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "Админка", description = "API принадлежащие админке")
@Path("/admin")
public class AdminResource {
  private final AuthService authService;
  private final TestService testService;
  private final QuestionService questionService;
  private final Configuration cfg;
  private final Model model;

  public AdminResource(AuthService authService, TestService testService, QuestionService questionService, Configuration cfg, Model model) {
    this.authService = authService;
    this.testService = testService;
    this.questionService = questionService;
    this.cfg = cfg;
    this.model = model;
  }

  @GET
  @Operation(summary = "Главная страница админки", description = "Проверяет наличие прав доступа и возвращает главную страницу")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Возвращает главную страницу админки"
  ), @ApiResponse(responseCode = "400", description = "Не достаточно прав")})
  @GeneratedValue(generator = MediaType.TEXT_HTML)
  public Response checkAuthorization(@CookieParam("Authorization") String token) throws TemplateException, IOException {
    if (notAdmin(token)) {
      return Response.status(400).entity("You don't have permission to access").build();
    }
    model.setName(authService.getUserWithToken(token));
    model.setMessage("Здравствуйте, " + model.getName());
    String page = processPage("index.ftlh");
    return Response.ok().entity(page).build();
  }

  private boolean notAdmin(String token) {
    return !authService.isAdmin(authService.getUserIdWithToken(token));
  }

  private String processPage(String pageTemplate) throws IOException, TemplateException {
    Template template = cfg.getTemplate(pageTemplate);
    Writer out = new StringWriter();
    template.process(model, out);
    return out.toString();
  }

  @GET
  @Path("test/new")
  @Operation(summary = "Страница добавления теста", description = "Проверяет наличие прав доступа и возвращает страницу")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Возвращает страницу добавления теста"
  ), @ApiResponse(responseCode = "400", description = "Не достаточно прав")})
  @GeneratedValue(generator = MediaType.TEXT_HTML)
  public Response newTest(@CookieParam("Authorization") String token) throws TemplateException, IOException {
    if (notAdmin(token)) {
      return Response.status(400).entity("You don't have permission to access").build();
    }
    return Response.ok().entity(processPage("new_test.ftlh")).build();
  }

  @POST
  @Path("test/add")
  @Operation(summary = "Сохранение теста", description = "Сохраняет тест и все вопросы в базе")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Возвращает главную страницу админки"
  ), @ApiResponse(responseCode = "400", description = "Не достаточно прав")})
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  public Response addTest(FormDataMultiPart multiPart,
                          @CookieParam("Authorization") String token)
      throws TemplateException, IOException {
    if (notAdmin(token)) {
      return Response.status(400).entity("You don't have permission to access").build();
    }
    Map<String, List<FormDataBodyPart>> formParams = multiPart.getFields();

    // Save test
    String name = formParams.get("testName").get(0).getValue();
    String description = formParams.get("description").get(0).getValue();
    int creatorId = authService.getUserIdWithToken(token);
    int questionsInAttempt = formParams.get("numberOfQuestionsInAttempt").get(0).getValueAs(Integer.class);
    int attemptQuantity = formParams.get("numberOfAttempts").get(0).getValueAs(Integer.class);
    int repeatInterval = formParams.get("timeBetweenCycles").get(0).getValueAs(Integer.class);
    short timeLimit = formParams.get("timeLimit").get(0).getValueAs(Short.class);
    int testId = testService.saveTest(name, description, creatorId, questionsInAttempt, attemptQuantity, repeatInterval, timeLimit);

//     Save questions
    List<Question> questionList = new ArrayList<>();
    int questionCount = formParams.get("questionCounter").get(0).getValueAs(Integer.class);
    //Iterate over questions
    for (int i = 1; i <= questionCount; i++) {
      Map<String, Map<String, String>> answerOptionsMap = new LinkedHashMap<>();
      int subAnswerCounter = formParams.get("subAnswerCounter" + i).get(0).getValueAs(Integer.class);
      //Iterate over sub answers
      for (int j = 1; j <= subAnswerCounter; j++) {
        Map<String, String> subAnswerMap = new LinkedHashMap<>();
        answerOptionsMap.put("answer" + j, subAnswerMap);
        int optionCounter = formParams.get("optionCounter" + i + "_" + j).get(0).getValueAs(Integer.class);
        //Collect answer options inside each sub answer
        for (int k = 1; k <= optionCounter; k++) {
          subAnswerMap.put(String.valueOf(k), formParams.get("answerOption" + i + "_" + j + "_" + k).get(0).getValue());
        }
      }
      QuestionAnswerType answerType = formParams.get("answerType" + i).get(0).getValueAs(QuestionAnswerType.class);
      String payload = buildPayload(answerOptionsMap, answerType);
      String answer = buildAnswer(formParams.get("answer" + i).get(0).getValue(), answerType);
      Question question = new Question();
      question.setTestId(testId);
      question.setWeight(formParams.get("weight" + i).get(0).getValueAs(Integer.class));
      question.setWording(formParams.get("question" + i).get(0).getValue());
      question.setAnswerType(answerType);
      question.setPayload(payload);
      question.setAnswer(answer);
      questionList.add(question);
    }
    int questionsSaved = questionService.saveQuestions(questionList);
    model.setMessage("Тест добавлен:\n" +
        "id: " + testId + "\n" +
        "Название: " + name + "\n" +
        "Описание: " + description + "\n" +
        "Вопросов добавлено: " + questionList.size() + "\n" +
        "Вопросов записано в БД: " + questionsSaved);
    return Response.ok().entity(processPage("index.ftlh")).build();
  }

  private String buildPayload(Map<String, Map<String, String>> answerOptionsMap, QuestionAnswerType answerType) throws JsonProcessingException {
    if (answerType == QuestionAnswerType.MULTIPLE_QUESTIONS_SINGLE_CHOICE) {
      return new ObjectMapper().writeValueAsString(answerOptionsMap);
    }
    return new ObjectMapper().writeValueAsString(answerOptionsMap.get("answer1"));
  }

  private String buildAnswer(String answerField, QuestionAnswerType answerType) throws JsonProcessingException {
    Map<String, String> map = new LinkedHashMap<>();
    switch (answerType) {
      case SINGLE_CHOICE:
        map.put("answer", answerField);
        break;
      case MULTIPLE_CHOICE:
        String[] multipleAnswers = answerField.replaceAll("\\s", "").split(",");
        for (int i = 1; i <= multipleAnswers.length; i++) {
          map.put("multiple_answer" + i, multipleAnswers[i - 1]);
        }
        break;
      case MULTIPLE_QUESTIONS_SINGLE_CHOICE:
        String[] multipleQuestions = answerField.replaceAll("\\s", "").split(",");
        for (int i = 1; i <= multipleQuestions.length; i++) {
          String[] answers = multipleQuestions[i - 1].split(":");
          map.put("answer" + i, answers[1]);
        }
        break;
      case RANKING:
        String[] rankingAnswers = answerField.replaceAll("\\s", "").split(",");
        for (int i = 1; i <= rankingAnswers.length; i++) {
          map.put(String.valueOf(i), rankingAnswers[i - 1]);
        }
        break;
    }
    return new ObjectMapper().writeValueAsString(map);
  }
}
