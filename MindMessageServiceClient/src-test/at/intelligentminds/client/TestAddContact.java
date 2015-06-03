package at.intelligentminds.client;

import static org.junit.Assert.*;

import org.json.JSONArray;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestAddContact {

  private ConnectionProvider provider;
  private String userEmail1;
  private String authTokenUser1;
  private String userEmail2;
  private String userEmail3;
  private String userEmail4;
  private String authTokenUser2;
  private String authTokenUser3;
  private String pw;

  @Before
  public void setUp() throws Exception {
    provider = ConnectionProvider.getInstance();
    userEmail1 = "test@test.at";
    userEmail2 = "demo@demo.at";
    userEmail3 = "demo2@demo.at";

    pw = "p@$$w0rD";

    provider.register(userEmail1, pw, "male", "first", "last");
    provider.register(userEmail2, pw, "male", "first", "last");
    provider.register(userEmail3, pw, "male", "first", "last");

    authTokenUser1 = provider.performLogin(userEmail1, pw);
    authTokenUser2 = provider.performLogin(userEmail2, pw);
    authTokenUser3 = provider.performLogin(userEmail3, pw);
  }

  @After
  public void tearDown() throws Exception {
    provider.deleteAccount(userEmail1, pw, authTokenUser1);
    provider.deleteAccount(userEmail2, pw, authTokenUser2);
    provider.deleteAccount(userEmail3, pw, authTokenUser3);
  }

  @Test
  public void testAddContact() {
    Boolean result = provider.addContact(userEmail1, userEmail2, authTokenUser1);

    assertTrue(result);
  }

  @Test
  public void testAddContactNotLoggedIn() {
    Boolean result = provider.addContact(userEmail1, userEmail2, "");

    assertFalse(result);
  }
  
  @Test
  public void testUserIsAlreadyContact() {
    Boolean result = provider.addContact(userEmail2, userEmail3, authTokenUser2);

    assertTrue(result);
    
    result = provider.addContact(userEmail3, userEmail2, authTokenUser3);
    assertFalse(result);
  }
}
