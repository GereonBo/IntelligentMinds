package at.intelligentminds.service;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.Transaction;

import at.intelligentminds.service.model.HibernateSupport;
import at.intelligentminds.service.model.User;

@Path("/userservice")
public class LoginService {

  private static Map<String,User> userTokens = new HashMap<String,User>();

  @Path("/login")
  @POST
  @Produces(MediaType.TEXT_PLAIN)
  public String login(@FormParam("email") String email, @FormParam("password") String password) {

    Transaction tx = HibernateSupport.getSession().beginTransaction();
    User user = (User)HibernateSupport.getSession().get(User.class, email);
    tx.commit();
    
    if(user != null){
      try {
        if(PasswordHash.validatePassword(password.toCharArray(), user.getPwHash())){
          String random_uuid = UUID.randomUUID().toString().toUpperCase() + '|' + email;
          userTokens.put(random_uuid, user);
          return random_uuid;
        }
      }
      catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        return "";
      }
    }
    return "";
  }
  
  @Path("/validate")
  @POST
  @Produces(MediaType.TEXT_PLAIN)
  public Boolean validate(@FormParam("token") String token) {
    if (userTokens.containsKey(token)) {
      return true;
    }
    
    return false;
  }
  
  @GET
  @Produces(MediaType.TEXT_HTML)
  public String get() {
    return "These are not the droids you are looking for.";
  }
  
  public static User getUserByToken(String token) {
    return userTokens.get(token);
  }
  
}
