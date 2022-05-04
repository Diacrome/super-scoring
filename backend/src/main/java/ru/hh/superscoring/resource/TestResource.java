package ru.hh.superscoring.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.superscoring.entity.Test;
import ru.hh.superscoring.service.TestService;

@Path("/test")
public class TestResource {

  private static final Logger logger = LoggerFactory.getLogger(TestResource.class);
  private final TestService testService;

  public TestResource(TestService testService) {
    this.testService = testService;
  }

  @GET
  @Path("info/{id}")
  @Produces("application/json")
  public Response getTestObject(@PathParam("id") int id) {
    Test test = testService.getTestById(id);
    if (test != null) {
      return Response.status(201).entity(test).build();
    } else {
      return Response.status(404, "There is no such test in the system").build();
    }
  }
}
