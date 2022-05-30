package ru.hh.superscoring.resource;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import org.hibernate.PropertyValueException;
import ru.hh.superscoring.service.AuthService;
import ru.hh.superscoring.service.QuestionService;
import ru.hh.superscoring.entity.Question;
import ru.hh.superscoring.util.Role;

@Path("/question")
public class QuestionResource {
  private final QuestionService questionService;
  private final AuthService authService;

  public QuestionResource(QuestionService questionService, AuthService authService) {
    this.questionService = questionService;
    this.authService = authService;
  }

  @PUT
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

  @PUT
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
  @Path("/add")
  @Consumes("application/json")
  public Response addQuestionToTest(Question question, @HeaderParam("authorization") String authorizationToken) {
    Role role;
    role = authService.getRoleByToken(authorizationToken);
    if (role == Role.ADMIN) {
      if (questionService.ifExistsTestFromQuestion(question) && questionService.addQuestion(question)) {
        return Response.status(201, "Question added").build();
      }
      return Response.status(404,"There is no such test in the system").build();
    }
    return Response.status(403, "Access denied").build();
  }

}
