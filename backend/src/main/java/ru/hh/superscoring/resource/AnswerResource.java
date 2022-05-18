package ru.hh.superscoring.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.hibernate.PropertyValueException;
import ru.hh.superscoring.service.AnswerService;
import ru.hh.superscoring.service.AuthService;

@Path("/answer")
public class AnswerResource {
  private final AnswerService answerService;
  private final AuthService authService;

  public AnswerResource(AnswerService answerService, AuthService authService) {
    this.answerService = answerService;
    this.authService = authService;
  }

  @POST
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
      answerService.saveAnswer(userId, question, answer);
    } catch (PropertyValueException pve) {
      return Response.status(400).entity("No testPass for the user!").build();
    } catch (Exception e) {
      return Response.status(400).entity("Unable to save answer!").build();
    }
    return Response.status(201).entity("Created").build();
  }
}
