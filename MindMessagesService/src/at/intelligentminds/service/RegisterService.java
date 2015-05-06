package at.intelligentminds.service;

import java.util.regex.Pattern;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import at.intelligentminds.service.model.UserHome;

@Path("/userservice")
public class RegisterService {
  
  private static final Pattern PASSWORD_VALIDATION_PATTERN = Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,}");
  private static final Pattern NAME_VALIDATOR = Pattern.compile("[A-Za-z\\.-]{2,}");
  private static final Pattern GENDER_VALIDATOR = Pattern.compile("^(fe)?male$");
  private static final Pattern EMAIL_VALIDATOR = Pattern.compile("[a-z0-9\\._-]+@[a-z0-9\\.-]+\\.[a-z]{2,3}");
  
  public enum RegisterResponse {
    SUCCESS, ERROR, PASSWORD, USER_EXISTS, NAME, MISC_ERROR, EMAIL
  }
  
  @POST
  @Path("/register")
  @Produces(MediaType.TEXT_PLAIN)
  public Integer login(@FormParam("username") String username, @FormParam("password") String password, 
      @FormParam("gender") String gender, @FormParam("firstName") String firstName, 
      @FormParam("lastName") String lastName) {

    if(!PASSWORD_VALIDATION_PATTERN.matcher(password).matches()){
      return RegisterResponse.PASSWORD.ordinal();
    }
    
    if(!NAME_VALIDATOR.matcher(firstName).matches() || !NAME_VALIDATOR.matcher(lastName).matches()){
      return RegisterResponse.NAME.ordinal();
    }
    
    if(!EMAIL_VALIDATOR.matcher(username).matches()){
      return RegisterResponse.EMAIL.ordinal();
    }
    
    if(!GENDER_VALIDATOR.matcher(gender).matches()){
      //Misc error because this can only be invalid if somebody tampered with the submission
      return RegisterResponse.MISC_ERROR.ordinal();
    }
    
    //TODO: Finish
    
    return RegisterResponse.SUCCESS.ordinal();
  }
  
  @Path("/register")
  @GET
  @Produces(MediaType.TEXT_HTML)
  public String get() {
    return "These are not the droids you are looking for.";
  }
  
}
