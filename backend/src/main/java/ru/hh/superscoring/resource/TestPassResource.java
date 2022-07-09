package ru.hh.superscoring.resource;


import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.awt.*;
import java.io.IOException;
import java.util.Set;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import org.hibernate.HibernateException;
import org.hibernate.PropertyValueException;
import ru.hh.superscoring.dto.LeaderBoardDto;
import ru.hh.superscoring.dto.QuestionsForTestDto;
import ru.hh.superscoring.dto.StartResultDto;
import ru.hh.superscoring.entity.TestPassQuestion;
import ru.hh.superscoring.util.exceptions.TestNoFilledException;
import ru.hh.superscoring.service.AuthService;
import ru.hh.superscoring.service.TestPassService;
import ru.hh.superscoring.service.TestService;
import ru.hh.superscoring.util.StartResult;

@Tag(name = "Прохождение теста", description = "API для взаимодействия с прохождениями тестов")
@Path("/")
public class TestPassResource {
  private final TestPassService testPassService;
  private final TestService testService;
  private final AuthService authService;

  public TestPassResource(TestPassService testPassService, TestService testService, AuthService authService) {
    this.testPassService = testPassService;
    this.testService = testService;
    this.authService = authService;
  }

  @POST
  @Operation(summary = "Начать тестирование", description = "Запускает процесс прохождения для указанного теста")
  @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Тестирование начато"
  ), @ApiResponse(responseCode = "401", description = "Токен не передан"
  ), @ApiResponse(responseCode = "404", description = "Ошибка авторизации или указанный тест отсутствует"
  ), @ApiResponse(responseCode = "400",
      description = "Тестирование не может быть начато: другой тест начат, превышено допустимое количество попыток, " +
          "тест успешно пройден недавно или вопросов недостаточно")})
  @Path("/start/{testId}")
  @Produces("application/json")
  public Response startTest(@PathParam("testId") Integer testId,
                            @HeaderParam("authorization") String authorizationToken
  ) {
    if (authorizationToken == null) {
      return Response.status(401).entity("No token found!").build();
    }
    Integer userId = authService.getUserIdWithToken(authorizationToken);
    if (userId == null) {
      return Response.status(404, "Invalid token!").build();
    }
    if (!testService.isExistActiveTest(testId)) {
      return Response.status(404, "There is no such test in the system ").build();
    }
    try {
      StartResultDto startResultDto = testPassService.startTest(testId, userId);
      if (startResultDto.getStartResult() == StartResult.ALREADY_STARTED) {
        return Response.status(400, "This user has already started the test")
            .build();
      }
      if (startResultDto.getStartResult() == StartResult.SPENT || startResultDto.getStartResult() == StartResult.PASSED) {
        return Response.status(400).entity(startResultDto).build();
      }
      return Response.status(201).build();
    } catch (TestNoFilledException tnf) {
      return Response.status(400).entity(tnf.getMessage()).build();
    } catch (HibernateException he) {
      return Response.status(400, "This test is not yet available for passing. Please try again later.")
          .build();
    }
  }

  @GET
  @Operation(summary = "Получение вопросов", description = "Получение вопросов текущего прохождения")
  @ApiResponses(value = {@ApiResponse(
      responseCode = "200", description = "возвращает id созданного теста",
      content = {@Content(schema = @Schema(implementation = QuestionsForTestDto.class))}
  ), @ApiResponse(responseCode = "401", description = "Токен не передан"
  ), @ApiResponse(responseCode = "404", description = "Ошибка авторизации или прохождение отсутствует")})
  @Path("/questions")
  @Produces("application/json")
  public Response getQuestions(@HeaderParam("authorization") String authorizationToken) throws JsonProcessingException {
    if (authorizationToken == null) {
      return Response.status(401).entity("No token found!").build();
    }
    Integer userId = authService.getUserIdWithToken(authorizationToken);
    if (userId == null) {
      return Response.status(404, "Invalid token!").build();
    }
    Set<TestPassQuestion> questions = testPassService.getUnfinishedPassQuestions(userId);
    if (questions.isEmpty()) {
      return Response.status(404, "There is no test pass record in the system").build();
    }
    return Response.ok(QuestionsForTestDto.map(questions)).build();
  }

  @GET
  @Operation(summary = "Получение таблицы лидеров", description = "Получение таблицы лидеров для указанного теста")
  @ApiResponses(value = {@ApiResponse(
      responseCode = "200", description = "возвращает id созданного теста",
      content = {@Content(schema = @Schema(implementation = LeaderBoardDto.class))}
  ), @ApiResponse(responseCode = "401", description = "Токен не передан"
  ), @ApiResponse(responseCode = "404", description = "Ошибка авторизации или прохождение отсутствует")})
  @Path("/leaders/{testId}")
  @Produces("application/json")
  public Response getLeaderBoard(@PathParam("testId") Integer testId,
                                 @QueryParam("page") @DefaultValue("0") Integer page,
                                 @QueryParam("perPage") @DefaultValue("10") Integer perPage) {

    return Response.ok(testPassService.getLeaders(testId, page, perPage)).build();
  }

  @POST
  @Operation(summary = "Отмена тестирования", description = "Завершает досрочно процесс прохождения теста")
  @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Тестирование отменено"
  ), @ApiResponse(responseCode = "401", description = "Токен не передан"
  ), @ApiResponse(responseCode = "404", description = "Ошибка авторизации"
  ), @ApiResponse(responseCode = "400",
      description = "Прохождение отсутствует или запись об отмене тестирования не удалось произвести корректно")})
  @Path("/cancel")
  public Response cancelTest(@HeaderParam("authorization") String authorizationToken) {
    if (authorizationToken == null) {
      return Response.status(401).entity("No token found!").build();
    }
    Integer userId = authService.getUserIdWithToken(authorizationToken);
    if (userId == null) {
      return Response.status(404, "Invalid token!").build();
    }
    try {
      testPassService.cancelTestPassByUserId(userId);
    } catch (HibernateException he) {
      return Response.status(400).entity(he.getMessage()).build();
    } catch (Exception e) {
      return Response.status(400).entity("Unable to cancel testPass!").build();
    }
    return Response.status(201).entity("Canceled!").build();
  }

  @GET
  @Operation(summary = "Получение истории прохождений пользователя", description = "Получение истории прохождения пользователем указанного теста")
  @ApiResponses(value = {@ApiResponse(
      responseCode = "200", description = "возвращает JSON объект с отображением порядкового номера на TestPassDTO",
      content = {@Content(schema = @Schema(implementation = LeaderBoardDto.class))}
  ), @ApiResponse(responseCode = "401", description = "Токен не передан"
  ), @ApiResponse(responseCode = "404", description = "Ошибка авторизации или прохождение отсутствует")})
  @Path("/all-passes-for-user")
  @Produces("application/json")
  public Response getAllTestPassesForUser(@HeaderParam("authorization") String authorizationToken,
                                          @QueryParam("page") @DefaultValue("0") int page,
                                          @QueryParam("perPage") @DefaultValue("10") int perPage,
                                          @QueryParam("testId") int testId) {
    if (authorizationToken == null) {
      return Response.status(401).entity("No token found!").build();
    }
    Integer userId = authService.getUserIdWithToken(authorizationToken);
    if (userId == null) {
      return Response.status(404, "Invalid token!").build();
    }
    return Response.ok(testPassService.getAllTestPassesForUser(page, perPage, testId, userId)).build();
  }

  @GET
  @Operation(summary = "Диаграмма: статистика по тесту",
      description = "Получение изображения диаграммы со статистикой прохождения теста для страницы результатов тестирования")
  @ApiResponses(value = {@ApiResponse(
      responseCode = "200", description = "возвращает изображение JPG",
      content = {@Content(schema = @Schema(implementation = LeaderBoardDto.class))}
  ), @ApiResponse(responseCode = "400", description = "Невозможно отобразить диаграмму"
  ), @ApiResponse(responseCode = "404", description = "Для этого прохождения не установлен результат")})
  @Path("/graph/{pass_id}")
  @Produces("image/jpg")
  public Response getGraph(@PathParam("pass_id") int testPassId) {
    try {
      return Response.ok(testPassService.getChartForResult(testPassId)).build();
    } catch (PropertyValueException pve) {
      return Response.status(404, "No result for this pass!").build();
    } catch (Exception ex) {
      return Response.status(400, "Unable to display chart").build();
    }
  }

}

