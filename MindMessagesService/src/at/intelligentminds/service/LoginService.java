package at.intelligentminds.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/userservice")
public class LoginService {
  
  private static List<String> userTokens = new Vector<String>();

  @Path("/login")
  @POST
  @Produces(MediaType.TEXT_PLAIN)
  public String login(@FormParam("username") String username, @FormParam("password") String password) {
    if ((username.equals("test") && password.equals("p@$$w0rd")) ||
        (username.equals("demo") && password.equals("demopassword"))) {
      String random_uuid = UUID.randomUUID().toString().toUpperCase() + '|' + username;
      userTokens.add(random_uuid);
      
      return random_uuid;
    }
    return "";
  }
  
  @Path("/validate")
  @POST
  @Produces(MediaType.TEXT_PLAIN)
  public Boolean validate(@FormParam("token") String token) {
    if (userTokens.contains(token)) {
      return true;
    }
    
    return false;
  }
  
  @GET
  @Produces(MediaType.TEXT_HTML)
  public String get() {
    return "These are not the droids you are looking for.";
  }

}
