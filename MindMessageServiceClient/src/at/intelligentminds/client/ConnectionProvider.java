package at.intelligentminds.client;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientResponse;

import at.intelligentminds.client.ConnectionProvider.RegisterResponse;

public class ConnectionProvider {

  private static ConnectionProvider instance;
  private WebTarget target;
  private String userId;

  public enum RegisterResponse {
    SUCCESS, ERROR, PASSWORD, USER_EXISTS, NAME, MISC_ERROR, EMAIL
  }

  private ConnectionProvider() {
    ClientConfig config = new ClientConfig();

    Client client = ClientBuilder.newClient(config);

    this.target = client.target(getBaseURI()).path("mm");
  }

  public static ConnectionProvider getInstance() {
    if (ConnectionProvider.instance == null) {
      ConnectionProvider.instance = new ConnectionProvider();
    }

    return ConnectionProvider.instance;
  }

  private static URI getBaseURI() {

    return UriBuilder.fromUri("http://localhost:8080/MindMessagesService").build();

  }

  public String performLogin(String email, String password) {

    Form login_form = new Form();
    login_form.param("email", email);
    login_form.param("password", password);

    userId = this.target.path("userservice").path("login").request().accept(MediaType.TEXT_PLAIN)
        .post(Entity.entity(login_form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), String.class);

    return userId;
  }

  public boolean validateLogin(String token) {
    Form validate_form = new Form();
    validate_form.param("token", token);

    boolean isLoggedIn = this.target.path("userservice").path("validate").request().accept(MediaType.TEXT_PLAIN)
        .post(Entity.entity(validate_form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), Boolean.class);

    return isLoggedIn;
  }

  public RegisterResponse register(String email, String password, String gender, String firstname, String lastname) {
    Form register_form = new Form();
    register_form.param("email", email);
    register_form.param("password", password);
    register_form.param("gender", gender);
    register_form.param("firstName", firstname);
    register_form.param("lastName", lastname);

    RegisterResponse response = RegisterResponse.values()[this.target.path("userservice").path("register").request()
        .accept(MediaType.TEXT_PLAIN)
        .post(Entity.entity(register_form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), Integer.class)];

    return response;
  }

  public boolean deleteAccount(String email, String password, String authtoken) {
    Form delete_form = new Form();
    delete_form.param("email", email);
    delete_form.param("password", password);
    delete_form.param("authtoken", authtoken);

    boolean response = this.target.path("userservice").path("deleteuser").request().accept(MediaType.TEXT_PLAIN)
        .post(Entity.entity(delete_form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), Boolean.class);

    return response;
  }
  
}
