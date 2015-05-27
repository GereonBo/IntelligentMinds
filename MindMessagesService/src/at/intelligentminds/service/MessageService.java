package at.intelligentminds.service;

import java.util.Date;
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
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.json.JSONArray;
import org.json.JSONObject;

import at.intelligentminds.service.model.HibernateSupport;
import at.intelligentminds.service.model.Message;
import at.intelligentminds.service.model.User;

@Path("/messageservice")
public class MessageService {

  @POST
  @Path("/createmessage")
  @Produces(MediaType.TEXT_PLAIN)
  public Boolean createMessage(@FormParam("senderEmail") String senderEmail,
      @FormParam("receiverEmail") String receiverEmail, @FormParam("text") String text,
      @FormParam("authtoken") String authtoken) {

    if (!new LoginService().validate(authtoken)) {
      return false;
    }

    Transaction tx = HibernateSupport.getSession().getTransaction();

    try {

      tx.begin();
      User sender = (User) HibernateSupport.getSession().get(User.class, senderEmail);
      User receiver = (User) HibernateSupport.getSession().get(User.class, receiverEmail);
      tx.commit();

      if (sender == null || receiver == null || text == null) {
        return false;
      }

      Message message = new Message();

      message.setText(text);
      message.setUserByUserReceiverId(receiver);
      message.setUserByUserSenderId(sender);
      message.setCreatonDate(new Date());

      tx = HibernateSupport.getSession().beginTransaction();
      boolean success = HibernateSupport.persist(message);
      tx.commit();

      return success;
    }
    catch (Exception e) {
      e.printStackTrace();
      tx.rollback();
    }

    return false;
  }

  @POST
  @Path("/retrievemessages")
  @Produces(MediaType.TEXT_PLAIN)
  public String retrieveMessage(@FormParam("senderEmail") String senderEmail,
      @FormParam("receiverEmail") String receiverEmail, @FormParam("authtoken") String authtoken) {

    if (!new LoginService().validate(authtoken)) {
      return "[]";
    }

    Transaction tx = HibernateSupport.getSession().getTransaction();

    tx.begin();

    User sender = (User) HibernateSupport.getSession().get(User.class, senderEmail);
    User receiver = (User) HibernateSupport.getSession().get(User.class, receiverEmail);

    if (sender == null || receiver == null) {
      tx.commit();
      return "[]";
    }

    Criterion c_sender_email = Restrictions.eq("userByUserSenderId", sender);
    Criterion c_receiver_email = Restrictions.eq("userByUserReceiverId", receiver);
    Criterion c_sender_email2 = Restrictions.eq("userByUserSenderId", receiver);
    Criterion c_receiver_email2 = Restrictions.eq("userByUserReceiverId", sender);

    Disjunction or_sender_email = Restrictions.disjunction();
    Disjunction or_receiver_email = Restrictions.disjunction();
    or_sender_email.add(c_sender_email);
    or_sender_email.add(c_sender_email2);
    or_receiver_email.add(c_receiver_email);
    or_receiver_email.add(c_receiver_email2);
    Criterion final_email = Restrictions.and(or_sender_email, or_receiver_email);

    Criteria criteria = HibernateSupport.getSession().createCriteria(Message.class, "message");
    criteria.add(final_email);

    criteria.addOrder(Order.asc("creatonDate"));
    
    criteria.createAlias("message.userByUserSenderId", "userByUserSenderId");
    criteria.createAlias("message.userByUserReceiverId", "userByUserReceiverId");
    
    criteria.setProjection(Projections.projectionList()
        .add(Projections.property("text"), "text")
        .add(Projections.property("creatonDate"), "creatonDate")
        .add(Projections.property("userByUserReceiverId.email"), "email")
        .add(Projections.property("userByUserSenderId.email"), "email"));

    JSONArray array = new JSONArray();

    try {
      List res = criteria.list();
      tx.commit();

      for(int i = 0; i < res.size(); i++) {
        
        Object [] messageValues = (Object [])res.get(i);

        JSONObject message = new JSONObject();
        message.put("text", messageValues[0]);
        message.put("creatonDate", messageValues[1]);
        message.put("receiverEmail", messageValues[2]);
        message.put("senderEmail", messageValues[3]);
        
        array.put(message);
      }      
    }
    catch (Exception e) {
      e.printStackTrace();
      tx.rollback();
    }

    return array.toString();
  }

  @GET
  @Produces(MediaType.TEXT_HTML)
  public String get() {
    return "These are not the droids you are looking for.";
  }
}
