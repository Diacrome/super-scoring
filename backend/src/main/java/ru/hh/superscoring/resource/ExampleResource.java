package ru.hh.superscoring.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import ru.hh.superscoring.entity.Test;
import ru.hh.superscoring.service.TestService;

@Path("/")
public class ExampleResource {
  private static final Logger logger = LoggerFactory.getLogger(ExampleResource.class);
  private final TestService testService;
  private final ObjectMapper mapper;

  public ExampleResource(TestService testService) {
    this.mapper = new ObjectMapper();
    this.testService = testService;
  }

  @GET
  public String hello() {
    return "Hello, SuperScoring team!";
  }

  @GET
  @Path("/test/{id}")
  @Produces("application/json")
  public Response getTestObject(@PathParam("id") String id) throws JsonProcessingException {
    Test answer = testService.getTestById(Integer.parseInt(id));
    if (answer != null) {
      return Response.ok(answer).build();
    }
    return Response.status(404, "There is no such test in the system").build();
  }
}
