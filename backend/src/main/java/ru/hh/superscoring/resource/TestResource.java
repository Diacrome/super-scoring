package ru.hh.superscoring.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import ru.hh.superscoring.dto.TestDto;
import ru.hh.superscoring.service.AuthService;
import ru.hh.superscoring.service.TestService;

@Path("/test")
public class TestResource {

  private final TestService testService;
  private final AuthService authService;

  public TestResource(TestService testService, AuthService authService) {
    this.testService = testService;
    this.authService = authService;
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

  @POST
  @Path("create")
  @Consumes({MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON})
  public Response createTest(@FormParam("name") String name,
                             @FormParam("description") String description,
                             @FormParam("questionCount") Integer questionCount,
                             @HeaderParam("authorization") String authorizationToken) {
    Integer userId = authService.getUserIdWithToken(authorizationToken);
    if (userId == null) {
      return Response.status(404, "Invalid token!").build();
    }
    boolean isUserAdmin = authService.isAdmin(userId);
    if (!isUserAdmin) {
      return Response.status(403, "Admin rights required").build();
    }
    Integer savedId;
    try {
      savedId = testService.saveTest(name, description, userId, questionCount);
    } catch (Exception e) {
      return Response.status(400).entity("Unable to save test!").build();
    }
    return Response.status(201).entity(savedId).build();
  }
}
