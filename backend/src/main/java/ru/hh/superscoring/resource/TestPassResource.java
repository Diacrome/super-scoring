package ru.hh.superscoring.resource;


import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import ru.hh.superscoring.service.QuestionService;
import ru.hh.superscoring.service.TestPassService;

@Path("/")
public class TestPassResource {
  private final QuestionService questionService;
  private final TestPassService testPassService;

  public TestPassResource(QuestionService questionService, TestPassService testPassService) {
    this.questionService = questionService;
    this.testPassService = testPassService;
  }

  @POST
  @Path("/start/{testId}")
  @Produces("application/json")
  public Response startTest(@PathParam("testId") Integer testId) {
    Integer userId = 1;
    if (testPassService.startTest(testId, userId)){
      return Response.status(201).build();
    }
    return Response.status(400, "This user has already started the test").build();
  }

  @GET
  @Path("/questions")
  @Produces("application/json")
  public Response getQuestions() throws JsonProcessingException {
    Integer userId = 4;
    List<Integer> questionsIds = testPassService.getUnfinishedPassQuestions(userId);
    if (questionsIds.isEmpty()) {
      return Response.status(404, "There is no test pass record in the system").build();
    }
    return Response.ok(questionService.getQuestionMapDto(questionsIds)).build();
  }
}
