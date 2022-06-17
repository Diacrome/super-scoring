package ru.hh.superscoring.resource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Tag(name = "Приветствие", description = "Ресурс с приветственным сообщением")
@Path("/hello")
public class HelloResource {

  @GET
  @Operation(summary = "Приветственная строка от команды",
      description = "Выводит приветственную строку")
  @ApiResponses(value = @ApiResponse(responseCode = "200", description = "Приветствие от команды"))
  public String hello() {
    return "Hello, Super-Scoring team!";
  }
}
