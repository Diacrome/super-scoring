package ru.hh.superscoring.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.hibernate.PropertyValueException;
import ru.hh.superscoring.service.AuthService;
import ru.hh.superscoring.service.QuestionService;
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
  @Consumes({MediaType.TEXT_HTML})
  public Response disableActivityQuestion(@QueryParam("questionId") int questionId,
                                          @HeaderParam("authorization") String authorizationToken) {
    if (authorizationToken == null) {
      return Response.status(401).entity("No token found!").build();
    }
    Role userRole = authService.getRoleByToken(authorizationToken);
    if (userRole == null) {
      return Response.status(404, "Invalid token!").build();
    }
    if (userRole != Role.ADMIN) {
      return Response.status(404, "Role user is not ADMIN. Access denied!").build();
    }
    try {
      questionService.setQuestionNotActivity(questionId);
    } catch (PropertyValueException pve) {
      return Response.status(400).entity("There is no question with such a QuestionId!").build();
    } catch (Exception e) {
      return Response.status(400).entity("Unable to set question is not activity!").build();
    }
    return Response.status(201).entity("Set question not activity with this QuestionId").build();
  }
}
