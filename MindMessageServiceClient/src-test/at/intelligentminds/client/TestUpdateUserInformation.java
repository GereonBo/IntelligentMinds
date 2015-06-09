package at.intelligentminds.client;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestUpdateUserInformation {

  private ConnectionProvider provider;
  private String userEmail1;
  private String authTokenUser1;
  private String pw;
  
  @Before
  public void setUp() throws Exception {
    provider = ConnectionProvider.getInstance();
    userEmail1 = "test@test.at";

    pw = "p@$$w0rD";
    
    provider.register(userEmail1, pw, "male", "first", "last");
    authTokenUser1 = provider.performLogin(userEmail1, pw);
  }
  
  @After
  public void tearDown() {
    
    provider.deleteAccount(userEmail1, pw, provider.performLogin(userEmail1, pw));
  }

  @Test
  public void testUpdateUserName() {
    Boolean updated = provider.updateUser(userEmail1, authTokenUser1, "nameNeu", "lastNeu", null);
    
    assertTrue(updated);
  }
  
  @Test
  public void testUpdateProfile() {
    Boolean updated = provider.updateUser(userEmail1, authTokenUser1, null, null, "newProfileText");
    
    assertTrue(updated);
  }
  
  @Test
  public void testNotLoggedIn() {
    Boolean updated = provider.updateUser(userEmail1, "", null, null, "newProfileText");
    
    assertFalse(updated);
  }
}
