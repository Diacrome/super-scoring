package ru.hh.superscoring.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.hibernate.PropertyValueException;
import ru.hh.superscoring.service.AnswerService;

@Path("/answer")
public class AnswerResource {
  private final AnswerService answerService;

  public AnswerResource(AnswerService answerService) {
    this.answerService = answerService;
  }

  @POST
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public Response saveAnswer(@FormParam("questionOrder") Integer question,
                             @FormParam("answer") String answer,
                             @HeaderParam("authorization") String authorizationToken
  ) {
    Integer userId = 1; // В SS-31 реализовано
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
