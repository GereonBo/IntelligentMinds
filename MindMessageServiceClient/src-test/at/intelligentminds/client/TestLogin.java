package at.intelligentminds.client;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestLogin {

  private ConnectionProvider provider;
  private String userEmail1;
  private String userEmail2;
  private String pw;
  
  @Before
  public void setUp() throws Exception {
    provider = ConnectionProvider.getInstance();
    userEmail1 = "test@test.at";
    userEmail2 = "demo@demo.at";
    pw = "p@$$w0rD";
    
    provider.register(userEmail1, pw, "male", "first", "last");
    provider.register(userEmail2, pw, "male", "first", "last");
  }

  @Test
  /**
   * should succeed
   */   
  public void testLoginSuccess() {
    String loginSucceeded = provider.performLogin(userEmail1, pw);
    
    assertNotNull(loginSucceeded);
    assertNotEquals("", loginSucceeded);
  }
  
  @Test
  /**
   * should fail
   * wrong user
   */   
  public void testLoginFails() {
    String loginFails = provider.performLogin("testNotAllowed", "p@$$w0rd");
    
    assertNotNull(loginFails);
    assertEquals("", loginFails);
  }
  
  @Test
  /**
   * should fail
   * wrong password
   */   
  public void testLoginFailsWrongPassword() {
    String loginFails = provider.performLogin(userEmail1, "ups");
    
    assertNotNull(loginFails);
    assertEquals("", loginFails);
  }

  @Test
  /**
   * Tests if login is remembered
   */
  public void testLoginRemembered()
  {
    String token_user_a = provider.performLogin(userEmail1, pw);
    String token_user_b = provider.performLogin(userEmail2, pw);
    
    assertNotNull(token_user_a);
    assertNotNull(token_user_b);
    assertNotEquals(token_user_a, "");
    assertNotEquals(token_user_b, "");
    
    boolean a_still_logged_in = provider.validateLogin(token_user_a);
    boolean b_still_logged_in = provider.validateLogin(token_user_b);
    boolean c_still_logged_in = provider.validateLogin("somegarbagetoken");
    
    assertTrue(a_still_logged_in);
    assertTrue(b_still_logged_in);
    assertFalse(c_still_logged_in);
  }
  
  @After
  public void tearDown() {
    
    provider.deleteAccount(userEmail1, pw, provider.performLogin(userEmail1, pw));
    provider.deleteAccount(userEmail2, pw, provider.performLogin(userEmail2, pw));
  }
}
