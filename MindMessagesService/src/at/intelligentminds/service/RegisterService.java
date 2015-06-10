package at.intelligentminds.service;

import java.util.regex.Pattern;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import at.intelligentminds.service.model.HibernateSupport;
import at.intelligentminds.service.model.User;

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
  public Integer login(@FormParam("email") String email, @FormParam("password") String password,
      @FormParam("gender") String gender, @FormParam("firstName") String firstName,
      @FormParam("lastName") String lastName) {

    if (!PASSWORD_VALIDATION_PATTERN.matcher(password).matches()) {
      return RegisterResponse.PASSWORD.ordinal();
    }

    if (!NAME_VALIDATOR.matcher(firstName).matches() || !NAME_VALIDATOR.matcher(lastName).matches()) {
      return RegisterResponse.NAME.ordinal();
    }

    if (!EMAIL_VALIDATOR.matcher(email).matches()) {
      return RegisterResponse.EMAIL.ordinal();
    }

    if (!GENDER_VALIDATOR.matcher(gender).matches()) {
      // Misc error because this can only be invalid if somebody tampered with
      // the submission
      return RegisterResponse.MISC_ERROR.ordinal();
    }

    Transaction tx = HibernateSupport.getSession().beginTransaction();
    Criteria criteria = HibernateSupport.getSession().createCriteria(User.class);
    criteria.add(Restrictions.like("email", email));
    int found = criteria.list().size();
    tx.commit();

    if (found > 0) {
      return RegisterResponse.USER_EXISTS.ordinal();
    }

    User newuser = new User();
    newuser.setEmail(email);
    newuser.setFirstName(firstName);
    newuser.setLastName(lastName);
    newuser.setGender(gender);

    String hash;
    try {
      hash = PasswordHash.createHash(password);
    }
    catch (Exception e) {
      e.printStackTrace();
      return RegisterResponse.MISC_ERROR.ordinal();
    }
    newuser.setPwHash(hash);

    tx = HibernateSupport.getSession().beginTransaction();
    boolean success = HibernateSupport.persist(newuser);
    tx.commit();

    if (success) {
      return RegisterResponse.SUCCESS.ordinal();
    }
    else {
      return RegisterResponse.MISC_ERROR.ordinal();
    }
  }

  @Path("/register")
  @GET
  @Produces(MediaType.TEXT_HTML)
  public String get() {
    return "These are not the droids you are looking for.";
  }

}
