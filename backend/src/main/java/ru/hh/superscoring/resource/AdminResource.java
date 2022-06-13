package ru.hh.superscoring.resource;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import ru.hh.superscoring.dto.Model;
import ru.hh.superscoring.entity.Question;
import ru.hh.superscoring.service.AuthService;
import ru.hh.superscoring.service.QuestionService;
import ru.hh.superscoring.service.TestService;
import ru.hh.superscoring.util.QuestionAnswerType;

import javax.persistence.GeneratedValue;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import java.util.Map;
import java.util.TreeMap;

@Path("/admin")
public class AdminResource {
  private final AuthService authService;
  private final TestService testService;
  private final QuestionService questionService;
  private final Configuration cfg;
  private final Model model;

  public AdminResource(AuthService authService, TestService testService, QuestionService questionService, Configuration cfg, Model model) {
    this.authService = authService;
    this.testService = testService;
    this.questionService = questionService;
    this.cfg = cfg;
    this.model = model;
  }

  @GET
  public Response checkAuthorization(@HeaderParam("authorization") String token) throws TemplateException, IOException {
    token = "557sa7";
    boolean isAdmin = authService.isAdmin(authService.getUserIdWithToken(token));

    if (isAdmin) {
      model.setName(authService.getUserWithToken(token));
      NewCookie cookie = new NewCookie("token",token);
      return Response.temporaryRedirect(URI.create("/admin/dashboard")).cookie(cookie).build();
    } else {
      return Response.status(400).entity("You don't have permission to access").build();
    }
  }

  @GET
  @Path("dashboard")
  @GeneratedValue(generator = MediaType.TEXT_HTML)
  public Response index(@CookieParam("token") String token) throws IOException, TemplateException {
      model.setMessage("Здравствуйте, " + model.getName());
      String page = processPage("index.ftlh");
      return Response.ok().entity(page).build();
//    return Response.status(400).entity("You don't have permission to access").build();
  }

  private String processPage(String pageTemplate) throws IOException, TemplateException {
    Template template = cfg.getTemplate(pageTemplate);
    Writer out = new StringWriter();
    template.process(model, out);
    return out.toString();
  }


  @GET
  @Path("new-test")
  @GeneratedValue(generator = MediaType.TEXT_HTML)
  public Response newTest(@CookieParam("token") String token) throws TemplateException, IOException {
    return Response.ok().entity(processPage("new-test.ftlh")).build();
  }

  @POST
  @Path("add-test")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public Response addTest(MultivaluedMap<String,String> formParams, @CookieParam("token") String token) throws TemplateException, IOException {
    // Save test
    String name = formParams.get("test-name").get(0);
    String description = formParams.get("description").get(0);
    int creatorId = authService.getUserIdWithToken(token);
    int questionCount = Integer.parseInt(formParams.get("question-counter").get(0).trim());
    int testId = testService.saveTest(name, description, creatorId, questionCount);

    Map<Integer, Boolean> savedQuestionTable = questionService.saveQuestionsFromForm(testId, formParams);


    model.setMessage("Тест добавлен:\n" +
        "id: " + testId + "\n" +
        "Название: " + name + "\n" +
        "Описание: " + description + "\n" +
        "Количество добавленных вопросов: " + testId);

    return Response.ok().entity(processPage("index.ftlh")).build();
  }


}
