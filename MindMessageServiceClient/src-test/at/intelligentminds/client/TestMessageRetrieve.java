package at.intelligentminds.client;

import static org.junit.Assert.*;

import org.json.JSONArray;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestMessageRetrieve {

private ConnectionProvider provider;
  
  private String userEmail1;  
  private String userEmail2;
  private String userEmail3;
  
  private String authTokenUser1;
  private String authTokenUser2;

  private String pw1;
  
  @Before
  public void setUp() throws Exception {
    userEmail1 = "usermustermann@bla.com";
    userEmail2 = "user0816@bla.com";
    userEmail3 = "user0817@bla.com";
    pw1 = "Passw0rd";
    
    provider = ConnectionProvider.getInstance();
    
    provider.register(userEmail1, pw1, "male", "user", "mustermann");
    provider.register(userEmail2, pw1, "male", "userZwei", "mustermann");
    provider.register(userEmail3, pw1, "male", "userDrei", "mustermann");

    authTokenUser1 = provider.performLogin(userEmail1, pw1); 
    authTokenUser2 = provider.performLogin(userEmail2, pw1);
    
    provider.sendMessage(userEmail1, userEmail2, "test", authTokenUser1);
    provider.sendMessage(userEmail2, userEmail1, "test 2", authTokenUser2);
    provider.sendMessage(userEmail2, userEmail3, "test drei", authTokenUser2);
  }

  @After
  public void tearDown() throws Exception {
    provider.deleteAccount(userEmail1, pw1, authTokenUser1);
    provider.deleteAccount(userEmail2, pw1, authTokenUser2);
    provider.deleteAccount(userEmail3, pw1, provider.performLogin(userEmail3, pw1));
  }

  @Test
  public void testGetCorrectMessages() {
    JSONArray response = provider.getMessagesBySenderAndReceiver(userEmail1, userEmail2, authTokenUser1);
    
    assertNotNull(response);
    assertEquals(2, response.length());
  }

  @Test
  public void testNotLoggedIn() {
    JSONArray response = provider.getMessagesBySenderAndReceiver(userEmail1, userEmail2, "");
    
    assertNotNull(response);
    assertEquals(0, response.length());
  }
  
  @Test
  public void testReceiverNotExists() {
    JSONArray response = provider.getMessagesBySenderAndReceiver(userEmail1, userEmail2, "");
    
    assertNotNull(response);
    assertEquals(0, response.length());
  }
  
  @Test
  public void testSenderNotExists() {
    JSONArray response = provider.getMessagesBySenderAndReceiver(userEmail1, userEmail2, "");
    
    assertNotNull(response);
    assertEquals(0, response.length());
  }
}
