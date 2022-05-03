package ru.hh.superscoring.resource;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.superscoring.entity.Test;
import ru.hh.superscoring.service.TestService;
import java.util.Optional;

@Path("/")
public class TestResource {

  private static final Logger logger = LoggerFactory.getLogger(TestResource.class);
  private final TestService testService;

  public TestResource(TestService testService) {
    this.testService = testService;
  }

  @POST
  @Path("ss/start/{id}")
  @Produces("application/json")
  public Response getTestObject(@PathParam("id") String id) {
    Optional<Test> test = testService.getTestById(id);
    if (test.isPresent()) {
      return Response.status(201)
          .entity(test)
          .build();
    }
    return Response.status(404, "There is no such test in the system").build();
  }
}
