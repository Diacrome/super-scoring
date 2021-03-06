package ru.hh.superscoring.resource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import ru.hh.superscoring.dto.StatusDto;
import ru.hh.superscoring.dto.TestBoardDto;
import ru.hh.superscoring.dto.TestBoardForUserDto;
import ru.hh.superscoring.dto.TestDto;
import ru.hh.superscoring.dto.TestPassDto;
import ru.hh.superscoring.dto.UserPassDto;
import ru.hh.superscoring.entity.QuestionDistribution;
import ru.hh.superscoring.entity.Test;
import ru.hh.superscoring.util.Role;
import ru.hh.superscoring.util.exceptions.DistributionAlreadyExistsException;
import ru.hh.superscoring.util.exceptions.DistributionNotFoundException;
import ru.hh.superscoring.util.exceptions.TestNoFilledException;
import ru.hh.superscoring.service.AuthService;
import ru.hh.superscoring.service.StatusService;
import ru.hh.superscoring.service.TestPassService;
import ru.hh.superscoring.service.TestService;

@Tag(name = "Тесты", description = "API для взаимодействия с тестами")
@Path("/test")
public class TestResource {

  private final TestService testService;
  private final StatusService statusService;
  private final AuthService authService;
  private final TestPassService testPassService;

  public TestResource(TestService testService, StatusService statusService, AuthService authService, TestPassService testPassService) {
    this.testService = testService;
    this.statusService = statusService;
    this.authService = authService;
    this.testPassService = testPassService;
  }

  @GET
  @Operation(summary = "Информация о тесте по id",
      description = "Получение начальных сведений о тесте по id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Возвращает описание теста",
          content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TestDto.class))}
      ), @ApiResponse(responseCode = "404", description = "Ошибка в случае отсутствия активного теста в системе"
  )})
  @Path("info/{id}")
  @Produces("application/json")
  public Response getTestObject(@PathParam("id") int id) {
    TestDto test = testService.getTestById(id);
    if (test != null) {
      return Response.status(201).entity(test).build();
    } else {
      return Response.status(404, "There is no such test in the system").build();
    }
  }

  @GET
  @Operation(summary = "Статус прохождения теста",
      description = "Возвращает статус прохождения теста для текущего пользователя по токену")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Возвращает статус прохождения",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = StatusDto.class))}
      ), @ApiResponse(responseCode = "401", description = "Ошибка авторизации"
  )})
  @Path("/status")
  @Produces("application/json")
  public Response getStatus(@HeaderParam("authorization") String token) {
    if (token == null) {
      return Response.status(401).entity("No token found").build();
    }
    return Response.status(200).entity(statusService.getStatus(token)).build();
  }

  @POST
  @Operation(summary = "Создание нового теста",
      description = "Создает новый тест. Доступно при наличии прав администратора.")
  @ApiResponses(value = {@ApiResponse(
      responseCode = "201", description = "возвращает id созданного теста",
      content = {@Content(schema = @Schema(implementation = int.class))}
  ), @ApiResponse(responseCode = "404", description = "Ошибка авторизации"
  ), @ApiResponse(responseCode = "403", description = "Недостаточно прав"
  ), @ApiResponse(responseCode = "400", description = "Ошибка при создании")})
  @Path("create")
  @Consumes({MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON})
  public Response createTest(@FormParam("name") String name,
                             @FormParam("description") String description,
                             @FormParam("questionCount") Integer questionCount,
                             @FormParam("attemptQuantity") Integer attemptQuantity,
                             @FormParam("repeatInterval") Integer repeatInterval,
                             @FormParam("timeLimit") short timeLimit,
                             @HeaderParam("authorization") String authorizationToken) {
    Integer userId = authService.getUserIdWithToken(authorizationToken);
    if (userId == null) {
      return Response.status(404, "Invalid token!").build();
    }
    boolean isUserAdmin = authService.isAdmin(userId);
    if (!isUserAdmin) {
      return Response.status(403, "Admin rights required").build();
    }
    Integer savedId;
    try {
      savedId = testService.saveTest(name, description, userId, questionCount, attemptQuantity, repeatInterval, timeLimit);
    } catch (Exception e) {
      return Response.status(400).entity("Unable to save test!").build();
    }
    return Response.status(201).entity(savedId).build();
  }

  @POST
  @Operation(summary = "Выключение теста", description = "Делает тест с указанным id неактивным")
  @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "При успешном отключении"
  ), @ApiResponse(responseCode = "404", description = "Ошибка авторизации"
  ), @ApiResponse(responseCode = "403", description = "Недостаточно прав"
  ), @ApiResponse(responseCode = "400", description = "Ошибка при сохранении")})
  @Path("off/{id}")
  @Produces("application/json")
  public Response inactivateTest(@PathParam("id") Integer testId,
                                 @HeaderParam("authorization") String authorizationToken) {
    Integer userId = authService.getUserIdWithToken(authorizationToken);
    if (userId == null) {
      return Response.status(404, "Invalid token!").build();
    }
    boolean isUserAdmin = authService.isAdmin(userId);
    if (!isUserAdmin) {
      return Response.status(403, "Admin rights required").build();
    }
    try {
      testService.switchOffTest(testId);
    } catch (Exception e) {
      return Response.status(400).entity("Unable to save test!").build();
    }
    return Response.status(201).entity("Test disabled").build();
  }

  @POST
  @Operation(summary = "Включение теста", description = "Делает тест с указанным id активным")
  @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "При успешном включении"
  ), @ApiResponse(responseCode = "404", description = "Ошибка авторизации"
  ), @ApiResponse(responseCode = "403", description = "Недостаточно прав"
  ), @ApiResponse(responseCode = "406", description = "Заданное в распределении количество вопросов неверно"
  ), @ApiResponse(responseCode = "400", description = "Ошибка при сохранении")})
  @Path("on/{id}")
  @Produces("application/json")
  public Response activateTest(@PathParam("id") Integer testId,
                               @HeaderParam("authorization") String authorizationToken) {
    Integer userId = authService.getUserIdWithToken(authorizationToken);
    if (userId == null) {
      return Response.status(404, "Invalid token!").build();
    }
    boolean isUserAdmin = authService.isAdmin(userId);
    if (!isUserAdmin) {
      return Response.status(403, "Admin rights required").build();
    }
    try {
      testService.switchOnTest(testId);
    } catch (TestNoFilledException tnf) {
      return Response.status(400).entity(tnf.getMessage()).build();
    } catch (Exception e) {
      return Response.status(400).entity("Unable to save test!").build();
    }
    return Response.status(201).entity("Test enabled").build();
  }

  @GET
  @Operation(summary = "Получение тестов", description = "Получение всех тестов. Доступно только с правами администратора")
  @ApiResponses(value = {@ApiResponse(
      responseCode = "201",
      description = "При успешном получении списка тестов",
      content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TestBoardDto.class))}
  ), @ApiResponse(responseCode = "401", description = "Токен не передан"
  ), @ApiResponse(responseCode = "404", description = "Ошибка авторизации"
  ), @ApiResponse(responseCode = "403", description = "Недостаточно прав")})
  @Path("/get-tests")
  @Produces("application/json")
  public Response getAllTests(@HeaderParam("authorization") String authorizationToken,
                              @QueryParam("page") @DefaultValue("0") Integer page,
                              @QueryParam("perPage") @DefaultValue("10") Integer perPage) {
    if (authorizationToken == null) {
      return Response.status(401).entity("No token found!").build();
    }
    Integer userId = authService.getUserIdWithToken(authorizationToken);
    if (userId == null) {
      return Response.status(404, "Invalid token!").build();
    }
    boolean isUserAdmin = authService.isAdmin(userId);
    if (!isUserAdmin) {
      return Response.status(403, "Admin rights required!").build();
    }
    List<Test> tests = testService.getAllTests(page, perPage);
    return Response.ok(TestBoardDto.map(tests, page, perPage)).build();
  }

  @GET
  @Operation(summary = "Получение тестов", description = "Получение всех тестов. Доступно для всех типов пользователей")
  @ApiResponses(value = {@ApiResponse(
      responseCode = "201",
      description = "При успешном получении списка тестов",
      content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TestBoardForUserDto.class))}
  ), @ApiResponse(responseCode = "401", description = "Токен не передан"
  ), @ApiResponse(responseCode = "404", description = "Ошибка авторизации")})
  @Path("/all-tests-for-user")
  @Produces("application/json")
  public Response getAllTestsForUser(@HeaderParam("authorization") String authorizationToken,
                                     @QueryParam("page") @DefaultValue("0") Integer page,
                                     @QueryParam("perPage") @DefaultValue("10") Integer perPage) {
    if (authorizationToken == null) {
      return Response.status(401).entity("No token found!").build();
    }
    Integer userId = authService.getUserIdWithToken(authorizationToken);
    if (userId == null) {
      return Response.status(404, "Invalid token!").build();
    }
    List<TestDto> tests = testService.getAllTestsForUser(page, perPage);
    return Response.ok(TestBoardForUserDto.map(tests, page, perPage)).build();
  }

  @GET
  @Operation(summary = "Получение результатов прохождения теста",
      description = "Получение результатов прохождения теста по id прохождения теста")
  @ApiResponses(value = {@ApiResponse(
      responseCode = "201",
      description = "При успешном получении результатов прохождения теста",
      content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TestPassDto.class))}
  ), @ApiResponse(responseCode = "404", description = "Отсутствует прохождение теста с таким id")})
  @Path("/results/{pass_id}")
  @Produces("application/json")
  public Response getTestPassResults(@PathParam("pass_id") int testPassId) {
    UserPassDto userPassDto = (UserPassDto) testPassService.getTestPassById(testPassId);
    if (userPassDto != null) {
      return Response.status(201).entity(userPassDto).build();
    } else {
      return Response.status(404, "There is no such 'TestPass' in the system").build();
    }
  }

  @POST
  @Operation(summary = "Добавление распределения вопросов к тесту", description = "Создает новое распределение")
  @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "При успешном добавлении распределения"
  ), @ApiResponse(responseCode = "404", description = "Отсутствие теста в который добавляется распределение"
  ), @ApiResponse(responseCode = "403", description = "Недостаточно прав")})
  @Path("/add-distribution")
  @Consumes("application/json")
  public Response addQuestionDistributionToTest(QuestionDistribution questionDistribution,
                                                @HeaderParam("authorization") String authorizationToken) {
    Role role;
    role = authService.getRoleByToken(authorizationToken);
    if (role == Role.ADMIN) {
      try {
        if (testService.getTestById(questionDistribution.getTestId()) != null &&
            testService.addQuestionDistribution(questionDistribution)) {
          return Response.status(201, "Question distribution added").build();
        }
      } catch (DistributionAlreadyExistsException e) {
        return Response.status(400).entity(e.getMessage()).build();
      }
      return Response.status(404, "There is no such test in the system").build();
    }
    return Response.status(403, "Access denied").build();
  }


  @DELETE
  @Operation(summary = "Удаление распределения", description = "Удаляет распределение по id")
  @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "При успешном удалении распределения"
  ), @ApiResponse(responseCode = "404", description = "Отсутствие распределения, которое необходимо удалить"
  ), @ApiResponse(responseCode = "403", description = "Недостаточно прав")})
  @Path("/remove-distribution/{questionDistributionId}")
  @Produces("application/json")
  public Response RemoveQuestionDistributionFromTest(@HeaderParam("authorization") String authorizationToken,
                                                     @PathParam("questionDistributionId") Integer distributionId) {
    Role role;
    role = authService.getRoleByToken(authorizationToken);
    if (role == Role.ADMIN) {
      try {
        testService.removeQuestionDistribution(distributionId);
      } catch (DistributionNotFoundException e) {
        return Response.status(404, "No distribution found").build();
      }
      return Response.status(201, "Distribution deleted").build();
    }
    return Response.status(403, "Access denied").build();
  }
}
