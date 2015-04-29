package at.intelligentminds.service;

import java.util.UUID;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/userservice")
public class RegisterService {
  
  public enum RegisterResponse {
    SUCCESS, ERROR, PASSWORD, USERNAME, EMAIL
  }
  
  @Path("/register")
  @POST
  @Produces(MediaType.TEXT_PLAIN)
  public RegisterResponse login(@FormParam("username") String username, @FormParam("password") String password, 
      @FormParam("gender") String gender, @FormParam("firstName") String firstName, 
      @FormParam("lastName") String lastName) {
    
    return RegisterResponse.SUCCESS;
  }
  
  @Path("/register")
  @GET
  @Produces(MediaType.TEXT_HTML)
  public String get() {
    return "These are not the droids you are looking for.";
  }
}
