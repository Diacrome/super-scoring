package ru.hh.superscoring.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import ru.hh.superscoring.dto.TestDto;
import ru.hh.superscoring.service.TestService;

@Path("/test")
public class TestResource {

  private final TestService testService;

  public TestResource(TestService testService) {
    this.testService = testService;
  }

  @GET
  @Path("info/{id}")
  @Produces("application/json")
  public Response getTestObject(@PathParam("id") int id) {
    TestDto test = testService.getTestById(id);
    if (test != null) {
      return Response.status(201).entity(test).build();
    } else {
      return Response.status(404, "There is no such test in the system").build();
    }
  }
}
