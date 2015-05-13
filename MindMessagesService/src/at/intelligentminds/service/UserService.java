package at.intelligentminds.service;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.Criteria;
import org.hibernate.Transaction;

import at.intelligentminds.service.model.HibernateSupport;
import at.intelligentminds.service.model.User;

@Path("/userservice")
public class UserService {

  @POST
  @Path("/searchaccount")
  @Produces(MediaType.TEXT_PLAIN)
  public List searchAccount(@FormParam("searchText") String searchText) {
    
    String[] parts = searchText.split(" ");

    Transaction tx = HibernateSupport.getSession().beginTransaction();
    Criterion cfirstname = Restrictions.like("email", ""+searchText+"%");
    Criterion clastname = Restrictions.like("firstName", "%"+searchText+"%");
    Criterion cemail = Restrictions.like("lastName", "%"+searchText+"%");
    Criterion cemail = Restrictions.like("accountName", "%"+searchText+"%");
    
    if(parts.length > 1)
    {
          for(int i = 0; i < parts.length;i++)
          {
            Criterion cfirstname = Restrictions.like("email", ""+parts[i]+"%");
            Criterion clastname = Restrictions.like("firstName", "%"+parts[i]+"%");
            Criterion cemail = Restrictions.like("lastName", "%"+parts[i]+"%");
            Criterion cemail = Restrictions.like("accountName", "%"+parts[i]+"%");
          }
    }
        
    Criteria criteria = HibernateSupport.getSession().createCriteria(User.class);
    criteria.add(Restrictions.or(cfirstname,clastname,cemail));
    criteria.setProjection(Projection.property("firstName"));
    criteria.setProjection(Projection.property("lastName"));
    criteria.setProjection(Projection.property("accountName"));
    
    return criteria.list();
  }
  
}
