package at.intelligentminds.client;

import static org.junit.Assert.*;

import org.json.JSONArray;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestGetContacts {

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
    userEmail4 = "demo3@demo.at";
    pw = "p@$$w0rD";

    provider.register(userEmail1, pw, "male", "first", "last");
    provider.register(userEmail2, pw, "male", "first", "last");
    provider.register(userEmail3, pw, "male", "first", "last");
    provider.register(userEmail4, pw, "male", "first", "last");

    authTokenUser1 = provider.performLogin(userEmail1, pw);
    authTokenUser2 = provider.performLogin(userEmail2, pw);
    authTokenUser3 = provider.performLogin(userEmail3, pw);
  }

  @After
  public void tearDown() throws Exception {
    provider.deleteAccount(userEmail1, pw, provider.performLogin(userEmail1, pw));
    provider.deleteAccount(userEmail2, pw, provider.performLogin(userEmail2, pw));
    provider.deleteAccount(userEmail3, pw, provider.performLogin(userEmail2, pw));
    provider.deleteAccount(userEmail4, pw, provider.performLogin(userEmail2, pw));
  }

  @Test
  public void testGetContactsZero() {
    JSONArray response = provider.getContacts(userEmail2, authTokenUser2);

    assertNotNull(response);
    assertEquals(0, response.length());
  }
  
  @Test
  public void testGetContactsNotLoggedIn() {
    JSONArray response = provider.getContacts(userEmail2, "");

    assertNotNull(response);
    assertEquals(0, response.length());
  }
  
  @Test
  public void testGetContacts() {
    provider.addContact(userEmail2, userEmail3, authTokenUser2);
    provider.addContact(userEmail2, userEmail4, authTokenUser2);
    
    JSONArray response = provider.getContacts(userEmail2, authTokenUser2);
    
    JSONArray response2 = provider.getContacts(userEmail3, authTokenUser3);

    assertNotNull(response);
    assertEquals(2, response.length());
    
    assertNotNull(response);
    assertEquals(1, response.length());
  }

}
