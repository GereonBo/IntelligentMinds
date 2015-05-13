package at.intelligentminds.client;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestLogin {

  private ConnectionProvider provider;
  
  @Before
  public void setUp() throws Exception {
    provider = ConnectionProvider.getInstance();
  }

  @Test
  /**
   * should succeed
   */   
  public void testLoginSuccess() {
    String loginSucceeded = provider.performLogin("test", "p@$$w0rd");
    
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
    String loginFails = provider.performLogin("test", "ups");
    
    assertNotNull(loginFails);
    assertEquals("", loginFails);
  }

  @Test
  /**
   * Tests if login is remembered
   */
  public void testLoginRemembered()
  {
    String token_user_a = provider.performLogin("test", "p@$$w0rd");
    String token_user_b = provider.performLogin("demo", "demopassword");
    
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
}
