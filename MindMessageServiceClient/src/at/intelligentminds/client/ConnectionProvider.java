package at.intelligentminds.client;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

public class ConnectionProvider {

  private static ConnectionProvider instance;
  private WebTarget target;

  private ConnectionProvider() {
    ClientConfig config = new ClientConfig();

    Client client = ClientBuilder.newClient(config);

    target = client.target(getBaseURI());
  }

  public static ConnectionProvider getInstance() {
    if (ConnectionProvider.instance == null) {
      ConnectionProvider.instance = new ConnectionProvider();
    }
    
    return ConnectionProvider.instance;
  }

  private static URI getBaseURI() {

    return UriBuilder.fromUri("http://localhost:8080/IntelligentMinds").build();

  }
}
