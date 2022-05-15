package ru.hh.superscoring.resource;

import javax.persistence.NoResultException;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import ru.hh.superscoring.entity.Token;
import ru.hh.superscoring.service.AuthService;
import ru.hh.superscoring.util.Role;


@Path("/auth")
public class AuthResource {

  private final AuthService authService;

  public AuthResource(AuthService authService) {
    this.authService = authService;
  }

  @GET
  @Path("/check-token")
  @Produces("application/json")
  public Response getUserByToken(@HeaderParam("authorization") String authorizationToken) {
    String userName;
    try {
      userName = authService.getUserWithToken(authorizationToken);
    } catch (NoResultException e) {
      return Response.status(404, "Invalid token").build();
    } catch (Exception e) {
      return Response.status(400, "Some error occurred").build();
    }
    String response = "{ \"name\" : \"" + userName + "\" }";
    return Response.ok(response).build();
  }

  @POST
  @Path("/token")
  @Produces("application/json")
  public Response generateToken(@FormParam("login") String login, @FormParam("password") String password) {
    Integer userId;
    try {
      userId = authService.checkAuthentication(login, password);
    } catch (NoResultException e) {
      return Response.status(404, "Invalid login or password").build();
    } catch (Exception e) {
      return Response.status(400, "Some error occurred").build();
    }
    Token newToken = authService.generateAccessToken(userId);
    String token = "{ \"token\" : \"" + newToken.getToken() + "\" }";
    return Response.ok(token).build();
  }

  @POST
  @Path("/register")
  @Produces("application/json")
  public Response addNewUser(@FormParam("login") String login, @FormParam("password") String password,
                             @FormParam("name") String name, @FormParam("role") String role) {
    if (authService.checkAuthenticationByLogin(login) == null) {
      authService.addUser(login, password, name, Role.valueOf(role));
      return Response.status(201, "User added").build();
    }
    return Response.status(401, "User already exists in the system").build();
  }

}