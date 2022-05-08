package ru.hh.superscoring.resource;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import ru.hh.superscoring.dto.UserDto;
import ru.hh.superscoring.entity.User;
import ru.hh.superscoring.entity.Token;
import ru.hh.superscoring.service.UserService;
import ru.hh.superscoring.service.AuthService;


@Path("/auth")
@Produces("application/json")
public class AuthResource {

  private final UserService userService;
  private final AuthService authService;

  public AuthResource(UserService userService, AuthService authService) {
    this.userService = userService;
    this.authService = authService;
  }

  @GET
  @Path("/t")
  public Response getUserByToken(@Context HttpHeaders headers) {

    String authorizationToken = headers.getRequestHeader("Authorization").get(0);
    UserDto user = userService.getUserById(authService.getUserWithToken(authorizationToken));
    if (user != null)
      return Response.status(201).entity(user).build();
    else
      return Response.status(404, "There is no such user in the system").build();
  }

}
