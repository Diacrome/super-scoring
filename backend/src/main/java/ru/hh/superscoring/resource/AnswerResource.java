package ru.hh.superscoring.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import ru.hh.superscoring.service.AnswerService;

@Path("/answer")
public class AnswerResource {
  private  final AnswerService answerService;

  public AnswerResource(AnswerService answerService){
    this.answerService = answerService;
  }

  @POST
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public Response saveAnswer(@FormParam("questionId") String questionString,
                             @FormParam("answer") String answer
                             ){
    //  @QueryParam(value = "testPassId") Integer testPassId // заготовка для получения userId
    Integer userId = 1; // Это просто заглушка для параметра, который пока не договорились как будет передаваться
    try {
      Integer questionId = Integer.parseInt(questionString);
      answerService.saveAnswer(userId, questionId, answer);
    }
    catch (NumberFormatException e){
      return Response.status(400).entity(e.toString()).build();
    }
    catch (Exception e){
      return Response.status(400).entity("Что-то пошло не так! " + e.toString()).build();
    }
    return Response.status(201).entity("Запись создана").build();
  }
}
