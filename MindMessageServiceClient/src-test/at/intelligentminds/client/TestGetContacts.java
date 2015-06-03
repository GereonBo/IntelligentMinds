package at.intelligentminds.client;

import static org.junit.Assert.*;

import java.util.ArrayList;

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
    provider.deleteAccount(userEmail3, pw, provider.performLogin(userEmail3, pw));
    provider.deleteAccount(userEmail4, pw, provider.performLogin(userEmail4, pw));
  }

  @Test
  public void testGetContactsZero() {
    ArrayList<User> users = provider.getContacts(userEmail2, authTokenUser2);

    assertNotNull(users);
    assertEquals(0, users.size());
  }
  
  @Test
  public void testGetContactsNotLoggedIn() {
    ArrayList<User> users = provider.getContacts(userEmail2, "");

    assertNotNull(users);
    assertEquals(0, users.size());
  }
  
  @Test
  public void testGetContacts() {
    provider.addContact(userEmail2, userEmail3, authTokenUser2);
    provider.addContact(userEmail2, userEmail4, authTokenUser2);
    
    ArrayList<User> users = provider.getContacts(userEmail2, authTokenUser2);
    
    ArrayList<User> users2 = provider.getContacts(userEmail3, authTokenUser3);

    assertNotNull(users);
    assertEquals(2, users.size());
    
    assertNotNull(users2);
    assertEquals(1, users2.size());
  }

}
