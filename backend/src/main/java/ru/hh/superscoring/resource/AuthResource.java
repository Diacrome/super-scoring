package ru.hh.superscoring.resource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import ru.hh.superscoring.entity.Token;
import ru.hh.superscoring.service.AuthService;
import ru.hh.superscoring.util.AuthUtil;

@Tag(name = "Авторизация", description = "API отвечающие за авторизацию и аутентификацию пользователя")
@Path("/auth")
public class AuthResource {

  private final AuthService authService;

  public AuthResource(AuthService authService) {
    this.authService = authService;
  }

  @GET
  @Operation(summary = "Авторизация по токену", description = "Возвращает имя пользователя, которому принадлежит токен")
  @ApiResponses(value = {@ApiResponse(
      responseCode = "200", description = "возвращает имя пользователя",
      content = {@Content(mediaType = "application/json",
          schema = @Schema(example = "name: Anna"))}
  ), @ApiResponse(responseCode = "404", description = "Ошибка авторизации"
  ), @ApiResponse(responseCode = "400", description = "Ошибка при получении имени")})
  @Path("/check-token")
  @Produces("application/json")
  public Response getUserByToken(@HeaderParam("authorization") String authorizationToken) {
    String userName;
    try {
      userName = authService.getUserNameByToken(authorizationToken);
    } catch (NoResultException e) {
      return Response.status(404, "Invalid token").build();
    } catch (Exception e) {
      return Response.status(400, "Some error occurred").build();
    }
    String response = "{ \"name\" : \"" + userName + "\" }";
    return Response.ok(response).build();
  }

  @POST
  @Operation(summary = "Авторизация по логину и паролю", description = "Создает и возвращает токен пользователя")
  @ApiResponses(value = {@ApiResponse(
      responseCode = "200", description = "возвращает токен",
      content = {@Content(mediaType = "application/json",
          schema = @Schema(example = "token: ktuq13qll08kmg8oc49b"))}
  ), @ApiResponse(responseCode = "404", description = "Ошибка авторизации"
  ), @ApiResponse(responseCode = "400", description = "Ошибка при создании токена")})
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
  @Operation(summary = "Регистрация пользователя", description = "Регистрирует пользователя не администратора")
  @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "При успешном добавлении пользователя"
  ), @ApiResponse(responseCode = "401", description = "Пользователь уже существует")})
  @Path("/register")
  @Produces("application/json")
  public Response addNewUser(@FormParam("login") String login, @FormParam("password") String password,
                             @FormParam("name") String name) {
    if (authService.getUserIdByLogin(login) == null) {
      authService.addNewUser(login, password, name);
      return Response.status(201, "User added").build();
    }
    return Response.status(401, "User already exists in the system").build();
  }

  @POST
  @Operation(summary = "Выдача прав администратора", description = "Делает обычного пользователя администратором")
  @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Указанный пользователь стал администратором"
  ), @ApiResponse(responseCode = "404", description = "Ошибка авторизации"
  ), @ApiResponse(responseCode = "403", description = "Недостаточно прав"
  ), @ApiResponse(responseCode = "400", description = "Ошибка при сохранении или указанный пользователь не найден")})
  @Path("/set-admin")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public Response setAdminRights(@FormParam("login") String login,
                                 @HeaderParam("authorization") String authorizationToken) {
    Response response = AuthUtil.checkForAdminRightsByToken(authService, authorizationToken);
    if (response.getStatus() == 201) {
      Integer newAdminId = authService.getUserIdByLogin(login);
      if (newAdminId == null) {
        return Response.status(400).build();
      }
      try {
        authService.setAdmin(newAdminId);
      } catch (Exception e) {
        return Response.status(400).build();
      }
      return Response.status(201, "created").build();
    }
    return response;
  }
}
