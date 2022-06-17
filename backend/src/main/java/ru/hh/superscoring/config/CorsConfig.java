package ru.hh.superscoring.config;


import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response;

@Path("/")
public class CorsConfig implements ContainerResponseFilter {

  @Override
  public void filter(ContainerRequestContext request,
                     ContainerResponseContext response) {
    response.getHeaders().add("Access-Control-Allow-Origin", "*");
    response.getHeaders().add("Access-Control-Allow-Headers", "Authorization, Content-Type");
    response.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, DELETE, OPTIONS, HEAD");
  }

  @OPTIONS
  public Response getOptions() {
    return Response.ok().build();
  }

}
