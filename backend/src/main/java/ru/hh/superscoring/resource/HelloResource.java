package ru.hh.superscoring.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
public class HelloResource {

  @GET
  public String hello() {
    return "Hello, Super-Scoring team!";
  }
}