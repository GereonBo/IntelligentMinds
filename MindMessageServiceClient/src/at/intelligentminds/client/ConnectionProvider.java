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

public class ConnectionProvider {

  private static ConnectionProvider instance;
  private WebTarget target;
  private String userId;

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

  public String performLogin(String user, String password) {

    Form login_form = new Form();
    login_form.param("username", user);
    login_form.param("password", password);
    
    String token = this.target.path("login").request().accept(MediaType.TEXT_PLAIN)
        .post(Entity.entity(login_form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), String.class);
    return token;
  }
}
