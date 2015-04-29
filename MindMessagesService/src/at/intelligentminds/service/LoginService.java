package at.intelligentminds.service;

import java.util.UUID;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/login")
public class LoginService {

  @POST
  @Produces(MediaType.TEXT_PLAIN)
  public String login(@FormParam("username") String username, @FormParam("password") String password) {
    if (username.equals("test") && password.equals("p@$$w0rd")) {
      String random_uuid = UUID.randomUUID().toString().toUpperCase() + '|' + username;
      return random_uuid;
    }
    return "";
  }

  @GET
  @Produces(MediaType.TEXT_HTML)
  public String get() {
    return "These are not the droids you are looking for.";
  }

}
