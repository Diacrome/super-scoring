package ru.hh.superscoring.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
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
  public Response getUserByToken(@HeaderParam("authorization") String authorizationToken) throws JsonProcessingException {
    if (authorizationToken != null) {
      String userName = userService.getUserNameByToken(authorizationToken);
      if (userName != null) {
        String response = "{ \"name\" : " + userName + " }";
        return Response.ok(response).build();
      }
      return Response.status(404, "There is no such user in the system").build();
    }
    return Response.status(404, "There is no such token in the system").build();
  }

  @POST
  @Path("/login/")
  @Produces("application/json")
  public Response userLogin(@FormParam("login") String login, @FormParam("password") String password) throws JsonProcessingException {
    Integer userId = authService.checkAuthentification(login, password);
    if (userId != null) {
      Token newToken = authService.generateAccessToken(userId);
      String token = "{ \"token\" : " + newToken.getToken() + " }";
      return Response.ok(token).build();
    }
    return Response.status(404, "Invalid login or password").build();
  }
}
