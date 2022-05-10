package ru.hh.superscoring.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
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
public class AuthResource {

  private final UserService userService;
  private final AuthService authService;

  public AuthResource(UserService userService, AuthService authService) {
    this.userService = userService;
    this.authService = authService;
  }

  @GET
  @Path("/token")
  @Produces("application/json")
  public Response getUserByToken(@HeaderParam("authorization") String authorizationToken) {

    if (authorizationToken != null) {
      String userName = userService.getUserNameById(authService.getUserWithToken(authorizationToken));
      if (userName != null) {
        String r = "User found : " + userName;
        return Response.status(201, r).build();
      }
      return Response.status(404, "There is no such user in the system").build();
    }
    return Response.status(404, "There is no such token in the system").build();
  }

  @POST
  @Path("/login/")
  @Produces("application/json")
  public Response userLogin(@PathParam("login") String login, @PathParam("password") String password) {
    Integer userId = authService.checkAuthentification(login, password);
    if (userId != null) {
      Token newToken = authService.generateAccessToken(userId);
      String token = newToken.getAccessToken();
      return Response.status(201, "User found, generated new token " + token).build();
    }
    return Response.status(404, "Invalid login or password").build();
  }
}
