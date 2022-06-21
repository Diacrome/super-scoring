package ru.hh.superscoring.util;

import javax.ws.rs.core.Response;
import ru.hh.superscoring.service.AuthService;

public class AuthUtil {
  public static Response checkForAdminRightsByToken(AuthService authService, String authorizationToken) {
    if (authorizationToken == null) {
      return Response.status(401).entity("No token found!").build();
    }
    Role userRole = authService.getRoleByToken(authorizationToken);
    if (userRole == null) {
      return Response.status(404, "Invalid token!").build();
    }
    if (userRole != Role.ADMIN) {
      return Response.status(403, "Role user is not ADMIN. Access denied!").build();
    }
    return Response.status(201).build();
  }

  public static Response checkForUserRightsByToken(AuthService authService, String authorizationToken) {
    if (authorizationToken == null) {
      return Response.status(401).entity("No token found!").build();
    }
    Role userRole = authService.getRoleByToken(authorizationToken);
    if (userRole == null) {
      return Response.status(404, "Invalid token!").build();
    }
    if (userRole != Role.USER || userRole != Role.ADMIN) {
      return Response.status(403, "Role user is not USER. Access denied!").build();
    }
    return Response.status(201).build();
  }
}
