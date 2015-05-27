package at.intelligentminds.service;

import java.util.List;

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
    
    if(!new LoginService().validate(authtoken)) return "[]";
    
    String[] parts = searchText.split(" ");

    Transaction tx = HibernateSupport.getSession().beginTransaction();
    Criterion cemail = Restrictions.like("email", "%"+searchText+"%");
    Criterion cfirstname = Restrictions.like("firstName", "%"+searchText+"%");
    Criterion clastname = Restrictions.like("lastName", "%"+searchText+"%");
    Criterion caccountname = Restrictions.like("accountName", "%"+searchText+"%");
    
    Disjunction or = Restrictions.disjunction();
    or.add(cemail);
    or.add(cfirstname);
    or.add(clastname);
    or.add(caccountname);
    
    if(parts.length > 1)
    {
          for(int i = 0; i < parts.length;i++)
          {
            cemail = Restrictions.like("email", "%"+parts[i]+"%");
            cfirstname = Restrictions.like("firstName", "%"+parts[i]+"%");
            clastname = Restrictions.like("lastName", "%"+parts[i]+"%");
            caccountname = Restrictions.like("accountName", "%"+parts[i]+"%");
            
            or.add(cemail);
            or.add(cfirstname);
            or.add(clastname);
            or.add(caccountname);
          }
    }
        
    Criteria criteria = HibernateSupport.getSession().createCriteria(User.class);
    criteria.add(or);
    
    criteria.setProjection(Projections.projectionList()
        .add(Projections.property("firstName"), "firstName")
        .add(Projections.property("lastName"), "lastName")
        .add(Projections.property("accountName"), "accountName")
        .add(Projections.property("email"), "email"))
        .setResultTransformer(Transformers.aliasToBean(User.class));
    
//    criteria.setProjection(Projections.property("firstName"));
//    criteria.setProjection(Projections.property("lastName"));
//    criteria.setProjection(Projections.property("accountName"));
    
    List res = criteria.list();
    
    JSONArray array = new JSONArray(res.toArray());

    tx.commit();
    return array.toString();
  }
  
  @Path("/searchaccount")
  @GET
  @Produces(MediaType.TEXT_HTML)
  public String get() {
    return "These are not the droids you are looking for.";
  }  
}


