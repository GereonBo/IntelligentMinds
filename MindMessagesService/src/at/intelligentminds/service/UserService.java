package at.intelligentminds.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.json.JSONArray;
import org.json.JSONObject;

import at.intelligentminds.service.model.HibernateSupport;
import at.intelligentminds.service.model.User;


@Path("/userservice")
public class UserService {

  @POST
  @Path("/searchaccount")
  @Produces(MediaType.TEXT_PLAIN)
  public String searchAccount(@FormParam("searchText") String searchText, @FormParam("authtoken") String authtoken) {    
    if(!new LoginService().validate(authtoken)) return "[]";
    
    User currentUser = LoginService.getUserByToken(authtoken);
    
    if(currentUser == null) {
      return "[]";
    }

    String[] parts = searchText.split(" ");

    Transaction tx = HibernateSupport.getSession().beginTransaction();
    Criterion cemail = Restrictions.like("email", "%" + searchText + "%");
    Criterion cfirstname = Restrictions.like("firstName", "%" + searchText + "%");
    Criterion clastname = Restrictions.like("lastName", "%" + searchText + "%");
    Criterion caccountname = Restrictions.like("accountName", "%" + searchText + "%");
    Criterion notCurrentUser = Restrictions.ne("email", currentUser.getEmail());

    Disjunction or = Restrictions.disjunction();
    or.add(cemail);
    or.add(cfirstname);
    or.add(clastname);
    or.add(caccountname);

    if (parts.length > 1) {
      for (int i = 0; i < parts.length; i++) {
        cemail = Restrictions.like("email", "%" + parts[i] + "%");
        cfirstname = Restrictions.like("firstName", "%" + parts[i] + "%");
        clastname = Restrictions.like("lastName", "%" + parts[i] + "%");
        caccountname = Restrictions.like("accountName", "%" + parts[i] + "%");

        or.add(cemail);
        or.add(cfirstname);
        or.add(clastname);
        or.add(caccountname);
      }
    }
    
    Criterion andCombined = Restrictions.and(or, notCurrentUser);

    Criteria criteria = HibernateSupport.getSession().createCriteria(User.class);
    criteria.add(andCombined);

    criteria.setProjection(
        Projections.projectionList().add(Projections.property("firstName"), "firstName")
            .add(Projections.property("lastName"), "lastName")
            .add(Projections.property("accountName"), "accountName")
            .add(Projections.property("email"), "email")).setResultTransformer(Transformers.aliasToBean(User.class));

    List res = criteria.list();

    JSONArray array = new JSONArray(res.toArray());

    tx.commit();
    
    return array.toString();    
    
  }
  
  @POST
  @Path("/addcontact")
  @Produces(MediaType.TEXT_PLAIN)
  public Boolean addContact(@FormParam("contactEmail") String contactEmail, 
      @FormParam("userEmail") String userEmail, @FormParam("authtoken") String authtoken) {

    if (!new LoginService().validate(authtoken)) {
      return false;
    }
    
    Transaction tx = HibernateSupport.getSession().getTransaction();
    tx.begin();
    User user = (User) HibernateSupport.getSession().get(User.class, userEmail);
    User contact = (User) HibernateSupport.getSession().get(User.class, contactEmail);

    if (user == null || contact == null || user.getUsersForContactId().contains(contact)) {
      tx.commit();
      return false;
    }
            
    user.getUsersForContactId().add(contact);
    contact.getUsersForContactId().add(user);
    
    List<Object> entities = new ArrayList<Object>();
    entities.add(user);
    entities.add(contact);
    
    try {
      Boolean result = HibernateSupport.persistMultiple(entities);
      
      tx.commit();
      
      return result;
    }
    catch(Exception e) {
      e.printStackTrace();
      
      if(tx != null) {
        tx.rollback();
      }
      
      return false;
    }  
  }
  
  @POST
  @Path("/retrievecontacts")
  @Produces(MediaType.TEXT_PLAIN)
  public String retrieveContacts(@FormParam("userEmail") String userEmail,
      @FormParam("authtoken") String authtoken) {

    try{
      if (!new LoginService().validate(authtoken)) {
        return "[]";
      }

      Transaction tx = HibernateSupport.getSession().getTransaction();
      tx.begin();
      User user = (User) HibernateSupport.getSession().get(User.class, userEmail);

      if (user == null) {
        tx.commit();
        return "[]";
      }

      Set<User> contacts = user.getUsersForContactId();
      ArrayList<User> newContacts = new ArrayList<User>();


      JSONArray array = new JSONArray();

      try {
        for(User contact : contacts) {
          User newContact = new User(contact.getEmail(), contact.getAccountName(), contact.getFirstName(), 
              contact.getLastName());

          newContacts.add(newContact);
        }

        array = new JSONArray(newContacts.toArray());
        tx.commit();
      }
      catch(Exception e) {
        e.printStackTrace();

        if(!tx.wasCommitted()) {
          tx.rollback();
        }
      } 
      return array.toString();   
    } catch (Exception e){
      e.printStackTrace();
    }
    return "[]";
  }
  
  @Path("/updateuser")
  @POST
  @Produces(MediaType.TEXT_PLAIN)
  public Boolean updateUser(@FormParam("userEmail") String email, @FormParam("authtoken") String authtoken, 
      @FormParam("firstName") String firstName, @FormParam("lastName") String lastName, 
      @FormParam("profileText") String profileText) {
    
    if (!new LoginService().validate(authtoken)) {
      return false;
    }
    
    Transaction tx = HibernateSupport.getSession().beginTransaction();
    
    try {     
  
      User user = (User) HibernateSupport.getSession().get(User.class, email);
      
      if(firstName != null) {
        user.setFirstName(firstName);
      }
      if(lastName != null) {
        user.setLastName(lastName);
      }
      if(profileText != null) {
        user.setProfileText(profileText);
      }
      
      HibernateSupport.persist(user);
      tx.commit();
    }
    catch (Exception e) {
      e.printStackTrace();
      if(tx != null) {
        tx.rollback();
      }
      
      return false;
    }   
    
    return true;
  }
  
  @POST
  @Path("/userinformation")
  @Produces(MediaType.TEXT_PLAIN)
  public String getUserInformation(@FormParam("contactEmail") String contactEmail,
      @FormParam("authtoken") String authtoken) {

    if (!new LoginService().validate(authtoken)) {
      return "";
    }
    
    Transaction tx = HibernateSupport.getSession().getTransaction();

    tx.begin();

    User user = (User) HibernateSupport.getSession().get(User.class, contactEmail);

    tx.commit();
    
    if (user == null) {      
      return "";
    }
    
    User retrievedUser = new User();
    retrievedUser.setAccountName(user.getAccountName() == null ? "" : user.getAccountName());
    retrievedUser.setEmail(user.getEmail());
    retrievedUser.setFirstName(user.getFirstName());
    retrievedUser.setLastName(user.getLastName());
    retrievedUser.setProfileText(user.getProfileText() == null ? "" : user.getProfileText());
    retrievedUser.setGender(user.getGender() == null ? "" : user.getGender());
    
    JSONObject userObject = new JSONObject(retrievedUser);
    
    return userObject.toString();   
  }
  
  @Path("/logout")
  @POST
  @Produces(MediaType.TEXT_PLAIN)
  public Boolean logout(@FormParam("email") String userEmail, @FormParam("authtoken") String authtoken) {
    
    if (!new LoginService().validate(authtoken)) {
      return false;
    }
    
    LoginService.removeUserByToken(authtoken);
    
    return true;
  }

  @Path("/searchaccount")
  @GET
  @Produces(MediaType.TEXT_HTML)
  public String get() {
    return "These are not the droids you are looking for.";
  }
}
