package at.intelligentminds.service;

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

import at.intelligentminds.service.model.HibernateSupport;
import at.intelligentminds.service.model.User;

@Path("/userservice")
public class UserService {

  @POST
  @Path("/searchaccount")
  @Produces(MediaType.TEXT_PLAIN)
  public String searchAccount(@FormParam("searchText") String searchText, @FormParam("authtoken") String authtoken) {

    if (!new LoginService().validate(authtoken)) return "[]";

    String[] parts = searchText.split(" ");

    Transaction tx = HibernateSupport.getSession().beginTransaction();
    Criterion cemail = Restrictions.like("email", "%" + searchText + "%");
    Criterion cfirstname = Restrictions.like("firstName", "%" + searchText + "%");
    Criterion clastname = Restrictions.like("lastName", "%" + searchText + "%");
    Criterion caccountname = Restrictions.like("accountName", "%" + searchText + "%");

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

    Criteria criteria = HibernateSupport.getSession().createCriteria(User.class);
    criteria.add(or);

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

    if (user == null || contact == null) {
      tx.commit();
      return false;
    }
    
    user.getUsersForContactId().add(contact);
    
    try {
      Boolean result = HibernateSupport.persist(user);
      tx.commit();
      
      return result;
    }
    catch(Exception e) {
      e.printStackTrace();
      return false;
    }  
  }
  
  @POST
  @Path("/retrievecontacts")
  @Produces(MediaType.TEXT_PLAIN)
  public String retrieveContacts(@FormParam("userEmail") String userEmail,
      @FormParam("authtoken") String authtoken) {

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
            
    Criteria criteria = HibernateSupport.getSession().createCriteria(User.class);
    
    criteria.createAlias("user.usersForContactId", "usersForContactId");
    
    criteria.add(Restrictions.eq("usersForContactId.user_id", userEmail));
    
    criteria.setProjection(
        Projections.projectionList().add(Projections.property("firstName"), "firstName")
            .add(Projections.property("lastName"), "lastName")
            .add(Projections.property("accountName"), "accountName")
            .add(Projections.property("email"), "email"))
            .setResultTransformer(Transformers.aliasToBean(User.class));

    List res = criteria.list();
    
    try {
      //Boolean result = HibernateSupport.persist(user);
      tx.commit();
      
      //return result;
    }
    catch(Exception e) {
      e.printStackTrace();
      //return false;
    }  
    
    return "";
  }

  @Path("/searchaccount")
  @GET
  @Produces(MediaType.TEXT_HTML)
  public String get() {
    return "These are not the droids you are looking for.";
  }
}
