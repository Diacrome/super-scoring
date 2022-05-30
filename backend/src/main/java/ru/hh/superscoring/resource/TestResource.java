package ru.hh.superscoring.resource;

import ru.hh.superscoring.dto.TestDto;
import ru.hh.superscoring.service.AuthService;
import ru.hh.superscoring.service.StatusService;
import ru.hh.superscoring.service.TestService;

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

@Path("/test")
public class TestResource {

  private final TestService testService;
  private final StatusService statusService;
  private final AuthService authService;

  public TestResource(TestService testService, StatusService statusService, AuthService authService) {
    this.testService = testService;
    this.statusService = statusService;
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

  @GET
  @Path("/status")
  @Produces("application/json")
  public Response getStatus(@HeaderParam("authorization") String token) {
    if (token == null) {
      return Response.status(401).entity("No token found").build();
    }
    return Response.status(200).entity(statusService.getStatus(token)).build();
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

  @POST
  @Path("off/{id}")
  @Produces("application/json")
  public Response inactivateTest(@PathParam("id") Integer testId,
                                 @HeaderParam("authorization") String authorizationToken) {
    Integer userId = authService.getUserIdWithToken(authorizationToken);
    if (userId == null) {
      return Response.status(404, "Invalid token!").build();
    }
    boolean isUserAdmin = authService.isAdmin(userId);
    if (!isUserAdmin) {
      return Response.status(403, "Admin rights required").build();
    }
    try {
      testService.switchOffTest(testId);
    } catch (Exception e) {
      return Response.status(400).entity("Unable to save test!").build();
    }
    return Response.status(201).build();
  }

  @POST
  @Path("on/{id}")
  @Produces("application/json")
  public Response activateTest(@PathParam("id") Integer testId,
                               @HeaderParam("authorization") String authorizationToken) {
    Integer userId = authService.getUserIdWithToken(authorizationToken);
    if (userId == null) {
      return Response.status(404, "Invalid token!").build();
    }
    boolean isUserAdmin = authService.isAdmin(userId);
    if (!isUserAdmin) {
      return Response.status(403, "Admin rights required").build();
    }
    try {
      testService.switchOnTest(testId);
    } catch (Exception e) {
      return Response.status(400).entity("Unable to save test!").build();
    }
    return Response.status(201).build();
  }
}
