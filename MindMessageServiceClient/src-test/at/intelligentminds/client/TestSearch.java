package at.intelligentminds.client;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import at.intelligentminds.client.ConnectionProvider.RegisterResponse;

public class TestSearch {

  private ConnectionProvider provider;
  
  private String userEmail1;
  private String authTokenUser1;
  private String userEmail2;
  private String userEmail3;
  private String userEmail4;
  private String pw1;
  
  @Before
  public void setUp() throws Exception {
    userEmail1 = "usermustermann@bla.com";
    userEmail2 = "user0816@bla.com";
    userEmail3 = "user0817@bla.com";
    userEmail4 = "nonsense@really.com";
    pw1 = "Passw0rd";
    
    provider = ConnectionProvider.getInstance();
    
    provider.register(userEmail1, pw1, "male", "user", "mustermann");
    provider.register(userEmail2, pw1, "male", "user2", "mustermann");
    provider.register(userEmail3, pw1, "male", "user3", "mustermann");
    provider.register(userEmail4, pw1, "male", "user4", "mustermann");
    authTokenUser1 = provider.performLogin(userEmail1, pw1);
  }

  @After
  public void tearDown() throws Exception {
    provider.deleteAccount(userEmail1, pw1, authTokenUser1);
    provider.deleteAccount(userEmail2, pw1, provider.performLogin(userEmail2, pw1));
    provider.deleteAccount(userEmail3, pw1, provider.performLogin(userEmail3, pw1));
    provider.deleteAccount(userEmail4, pw1, provider.performLogin(userEmail4, pw1));
  }

  @Test
  public void testSearchNotFound() {
    List response = provider.searchAccounts("nope", authTokenUser1);
    
    assertNull(response);
    
  }
  
  @Test
  public void testSearchNotLoggedIn() {
    List response = provider.searchAccounts("user", "");
    
    assertNull(response);    
  }
  
  @Test
  public void testSearchFound() {
    List response = provider.searchAccounts("user", authTokenUser1);
    
    assertNotNull(response);
    assertEquals(2, response.size());
  }
}
