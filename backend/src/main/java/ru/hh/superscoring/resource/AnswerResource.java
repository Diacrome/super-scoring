package ru.hh.superscoring.resource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.hibernate.HibernateException;
import ru.hh.superscoring.service.AnswerService;
import ru.hh.superscoring.service.AuthService;
import ru.hh.superscoring.util.exceptions.TestPassTimeoutException;

@Tag(name = "Ответы", description = "API для взаимодействия с ответами")
@Path("/answer")
public class AnswerResource {
  private final AnswerService answerService;
  private final AuthService authService;

  public AnswerResource(AnswerService answerService, AuthService authService) {
    this.answerService = answerService;
    this.authService = authService;
  }

  @POST
  @Operation(summary = "Сохранение ответа", description = "Сохранят ответ пользователя на вопрос")
  @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "При успешном сохранении"
  ), @ApiResponse(responseCode = "401", description = "Не передан токен пользователя"
  ), @ApiResponse(responseCode = "404", description = "Ошибка авторизации"
  ), @ApiResponse(responseCode = "400", description = "Ошибка при сохранении или ответ отклонен валидатором")})
  @Consumes({MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON})
  public Response saveAnswer(@FormParam("questionOrder") Integer question,
                             @FormParam("answer") String answer,
                             @HeaderParam("authorization") String authorizationToken
  ) {
    if (authorizationToken == null) {
      return Response.status(401).entity("No token found!").build();
    }
    Integer userId = authService.getUserIdWithToken(authorizationToken);
    if (userId == null) {
      return Response.status(404, "Invalid token!").build();
    }
    try {
      if (!answerService.validateAnswer(answer, userId, question)) {
        return Response.status(400).entity("Answer format does not match the question").build();
      }
      answerService.saveAnswer(userId, question, answer);
    } catch (HibernateException he) {
      return Response.status(400).entity(he.getMessage()).build();
    } catch (TestPassTimeoutException e) {
      return Response.status(400).entity("Time limit for test exceeded!").build();
    } catch (Exception e) {
      return Response.status(400).entity("Unable to save answer!").build();
    }
    return Response.status(201).entity("Created").build();
  }
}
