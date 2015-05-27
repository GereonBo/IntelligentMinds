package at.intelligentminds.client;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestAddContact {

  private ConnectionProvider provider;
  private String userEmail1;
  private String authTokenUser1;
  private String userEmail2;
  private String authTokenUser2;
  private String pw;

  @Before
  public void setUp() throws Exception {
    provider = ConnectionProvider.getInstance();
    userEmail1 = "test@test.at";
    userEmail2 = "demo@demo.at";
    pw = "p@$$w0rD";

    provider.register(userEmail1, pw, "male", "first", "last");
    provider.register(userEmail2, pw, "male", "first", "last");

    authTokenUser1 = provider.performLogin(userEmail1, pw);
  }

  @After
  public void tearDown() throws Exception {
    provider.deleteAccount(userEmail1, pw, provider.performLogin(userEmail1, pw));
    provider.deleteAccount(userEmail2, pw, provider.performLogin(userEmail2, pw));
  }

  @Test
  public void testAddContact() {
    Boolean result = provider.addContact(userEmail1, userEmail2, authTokenUser1);

    assertTrue(result);
  }

  @Test
  public void testAddContactNotLoggedIn() {
    Boolean result = provider.addContact(userEmail1, userEmail2, "");

    assertTrue(result);
  }
}
