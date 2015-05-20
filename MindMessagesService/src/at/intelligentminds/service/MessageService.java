package at.intelligentminds.service;

import java.util.UUID;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.Transaction;

import at.intelligentminds.service.RegisterService.RegisterResponse;
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

    try {
      Transaction tx = HibernateSupport.getSession().beginTransaction();
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

      tx = HibernateSupport.getSession().beginTransaction();
      boolean success = HibernateSupport.persist(message);
      tx.commit();

      return success;
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return false;
  }
}
