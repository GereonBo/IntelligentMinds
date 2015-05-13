package at.intelligentminds.service;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.Transaction;

import at.intelligentminds.service.model.HibernateSupport;
import at.intelligentminds.service.model.User;

@Path("/userservice")
public class DeleteService {
  
  @POST
  @Path("/deleteuser")
  @Produces(MediaType.TEXT_PLAIN)
  public boolean deleteUser(@FormParam("email") String email, @FormParam("password") String password, 
      @FormParam("authtoken") String authtoken) {

    if(!new LoginService().validate(authtoken)) return false;
    
    Transaction tx = HibernateSupport.getSession().beginTransaction();
    User user = (User)HibernateSupport.getSession().get(User.class, email);
    tx.commit();
    
    try {
      if(PasswordHash.validatePassword(password, user.getPwHash())){
        tx = HibernateSupport.getSession().beginTransaction();
        HibernateSupport.getSession().delete(user);
        tx.commit();
        return true;
      }else{
        return false;
      }
    }
    catch (Exception e) {
      e.printStackTrace();
      return false;
    }    
  }
  
  @Path("/deleteuser")
  @GET
  @Produces(MediaType.TEXT_HTML)
  public String get() {
    return "These are not the droids you are looking for.";
  }
}
