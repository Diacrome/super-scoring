package ru.hh.superscoring.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/helloSuperScoringTeam")
public class ExampleResource {
    private static final Logger logger = LoggerFactory.getLogger(ExampleResource.class);

    @GET
    public String hello() {
        return "Hello, SuperScoring team!";
    }

}
