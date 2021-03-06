package ru.hh.superscoring.resource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import org.hibernate.PropertyValueException;
import ru.hh.superscoring.entity.Question;
import ru.hh.superscoring.dto.QuestionBoardDto;
import ru.hh.superscoring.service.AuthService;
import ru.hh.superscoring.service.QuestionService;
import ru.hh.superscoring.util.Role;
import java.util.List;

@Tag(name = "Вопросы", description = "API для взаимодействия с вопросами")
@Path("/question")
public class QuestionResource {
  private final QuestionService questionService;
  private final AuthService authService;

  public QuestionResource(QuestionService questionService, AuthService authService) {
    this.questionService = questionService;
    this.authService = authService;
  }

  @PUT
  @Operation(summary = "Выключение вопроса", description = "Делает вопрос с указанным id неактивным")
  @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "При успешном отключении вопроса"
  ), @ApiResponse(responseCode = "401", description = "Токен не передан"
  ), @ApiResponse(responseCode = "404", description = "Ошибка авторизации"
  ), @ApiResponse(responseCode = "403", description = "Недостаточно прав"
  ), @ApiResponse(responseCode = "400", description = "Ошибка при сохранении или указанный вопрос не найден")})
  @Path("/inactive/{questionId}")
  public Response disableActiveQuestion(@PathParam("questionId") int questionId,
                                        @HeaderParam("authorization") String authorizationToken) {
    Response response = checkAuthorization(authorizationToken);
    if (response.getStatus() == 201) {
      try {
        questionService.setQuestionNotActive(questionId);
      } catch (PropertyValueException pve) {
        return Response.status(400).entity("There is no question with such a QuestionId!").build();
      } catch (Exception e) {
        return Response.status(400).entity("Unable to set question is not 'active'!").build();
      }
      return Response.status(201).entity("Set question not 'active' with this QuestionId").build();
    }
    return response;
  }

  @GET
  @Operation(summary = "Получение вопросов",
      description = "Получение вопросов относящихся к данному тесту по id теста. Доступно только с правами администратора")
  @ApiResponses(value = {@ApiResponse(
      responseCode = "201",
      description = "При успешном получении списка вопросов",
      content = {@Content(mediaType = "application/json", schema = @Schema(implementation = QuestionBoardDto.class))}
  ), @ApiResponse(responseCode = "401", description = "Токен не передан"
  ), @ApiResponse(responseCode = "404", description = "Ошибка авторизации"
  ), @ApiResponse(responseCode = "403", description = "Недостаточно прав"
  ), @ApiResponse(responseCode = "400", description = "Вопросов для указанного теста не найдено")})
  @Path("/all-questions-in-test/{testId}")
  @Produces("application/json")
  public Response getAllQuestionsForTest(@PathParam("testId") int testId,
                                         @HeaderParam("authorization") String authorizationToken,
                                         @QueryParam("page") @DefaultValue("0") Integer page,
                                         @QueryParam("perPage") @DefaultValue("10") Integer perPage) {
    Response response = checkAuthorization(authorizationToken);
    if (response.getStatus() == 201) {
      try {
        List<Question> questions = questionService.getAllQuestionsForTest(testId, page, perPage);
        return Response.ok(QuestionBoardDto.map(questions, page, perPage)).build();
      } catch (PropertyValueException pve) {
        return Response.status(400).entity("There is no question with such a testId!").build();
      }
    }
    return response;
  }

  @GET
  @Operation(summary = "Получение вопросов",
      description = "Получение всех вопросов. Доступно только с правами администратора")
  @ApiResponses(value = {@ApiResponse(
      responseCode = "201",
      description = "При успешном получении списка вопросов",
      content = {@Content(mediaType = "application/json", schema = @Schema(implementation = QuestionBoardDto.class))}
  ), @ApiResponse(responseCode = "401", description = "Токен не передан"
  ), @ApiResponse(responseCode = "404", description = "Ошибка авторизации"
  ), @ApiResponse(responseCode = "403", description = "Недостаточно прав")})
  @Path("/all-questions")
  @Produces("application/json")
  public Response getAllQuestions(@HeaderParam("authorization") String authorizationToken,
                                  @QueryParam("page") @DefaultValue("0") Integer page,
                                  @QueryParam("perPage") @DefaultValue("10") Integer perPage) {
    Response response = checkAuthorization(authorizationToken);
    if (response.getStatus() == 201) {
      List<Question> questions = questionService.getAllQuestions(page, perPage);
      return Response.ok(QuestionBoardDto.map(questions, page, perPage)).build();
    }
    return response;
  }

  @PUT
  @Operation(summary = "Включение вопроса", description = "Делает вопрос с указанным id активным")
  @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "При успешном включении вопроса"
  ), @ApiResponse(responseCode = "401", description = "Токен не передан"
  ), @ApiResponse(responseCode = "404", description = "Ошибка авторизации"
  ), @ApiResponse(responseCode = "403", description = "Недостаточно прав"
  ), @ApiResponse(responseCode = "400", description = "Ошибка при сохранении или указанный вопрос не найден")})
  @Path("/active/{questionId}")
  public Response enableInactiveQuestion(@PathParam("questionId") int questionId,
                                         @HeaderParam("authorization") String authorizationToken) {
    Response response = checkAuthorization(authorizationToken);
    if (response.getStatus() == 201) {
      try {
        questionService.setQuestionActive(questionId);
      } catch (PropertyValueException pve) {
        return Response.status(400).entity("There is no question with such a QuestionId!").build();
      } catch (Exception e) {
        return Response.status(400).entity("Unable to set question is 'active'!").build();
      }
      return Response.status(201).entity("Set question 'active' with this QuestionId").build();
    }
    return response;
  }

  private Response checkAuthorization(String authorizationToken) {
    if (authorizationToken == null) {
      return Response.status(401).entity("No token found!").build();
    }
    Role userRole = authService.getRoleByToken(authorizationToken);
    if (userRole == null) {
      return Response.status(404, "Invalid token!").build();
    }
    if (userRole != Role.ADMIN) {
      return Response.status(403, "Role user is not ADMIN. Access denied!").build();
    }
    return Response.status(201).build();
  }

  @POST
  @Operation(summary = "Добавление вопроса", description = "Создает новый вопрос")
  @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "При успешном добавлении вопроса"
  ), @ApiResponse(responseCode = "404", description = "Отсутствие теста в который добавляется вопрос"
  ), @ApiResponse(responseCode = "403", description = "Недостаточно прав")})
  @Path("/add")
  @Consumes("application/json")
  public Response addQuestionToTest(Question question, @HeaderParam("authorization") String authorizationToken) {
    Role role;
    role = authService.getRoleByToken(authorizationToken);
    if (role == Role.ADMIN) {
      try {
        if (questionService.ifExistsTestFromQuestion(question) && questionService.addQuestion(question)) {
          return Response.status(201, "Question added").build();
        }
      }catch (IllegalArgumentException exception){
        return Response.status(400, exception.getMessage()).build();
      }
      return Response.status(404, "There is no such test in the system").build();
    }
    return Response.status(403, "Access denied").build();
  }

}
