package at.intelligentminds.client;

import static org.junit.Assert.*;
import junit.framework.Assert;

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
  
}
