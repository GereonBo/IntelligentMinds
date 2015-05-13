package at.intelligentminds.client;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import at.intelligentminds.client.ConnectionProvider.RegisterResponse;

public class TestRegister {

  private ConnectionProvider provider;
  private String user1;
  private String user2;
  private String user3;
  private String pw1;
  private String pw2;

  @Before
  public void setUp() throws Exception {
    user1 = "usermustermann@bla.com";
    user2 = "user0816@bla.com";
    user3 = "user0817@bla.com";
    pw1 = "Passw0rd";

    provider = ConnectionProvider.getInstance();
  }

  @Test
  /**
   * should succeed
   */   
  public void testRegisterSucceed() {
    RegisterResponse response = provider.register(user1, pw1, "male", "Thomas", "Hoedl");
    
    assertNotNull(response);
    assertEquals(RegisterResponse.SUCCESS, response);
  }
  
  @Test
  @Ignore
  /**
   * test username already exists
   */   
  public void testRegisterUsernameExists() {
    RegisterResponse response = provider.register(user2, pw1, "male", "user", "mustermann");
    RegisterResponse response2 = provider.register(user2, pw1, "male", "user", "mustermann");
    
    assertNotNull(response);
    assertNotNull(response2);
    assertEquals(RegisterResponse.SUCCESS, response);
    assertEquals(RegisterResponse.USER_EXISTS, response);
  }
  
  @Test
  /**
   * test username already exists
   */   
  public void testRegisterWeakPassword() {
    RegisterResponse response = provider.register(user3, "testfere", "male", "max", "mustermann");
    
    assertNotNull(response);
    assertEquals(RegisterResponse.PASSWORD, response);
    
    response = provider.register(user3, "test1234", "male", "user", "mustermann");
    
    assertNotNull(response);
    assertEquals(RegisterResponse.PASSWORD, response);
    
    response = provider.register(user3, "test13T", "male", "user", "mustermann");
    
    assertNotNull(response);
    assertEquals(RegisterResponse.PASSWORD, response);
    
    response = provider.register(user3, "ERRRWWQQT", "male", "user", "mustermann");
    
    assertNotNull(response);
    assertEquals(RegisterResponse.PASSWORD, response);
  }
  
  @Test
  /**
   * wrong email
   */   
  public void testRegisterEmailfail() {
    RegisterResponse response = provider.register("wrongEmail", pw1, "male", "user", "mustermann");
    
    assertNotNull(response);
    assertEquals(RegisterResponse.EMAIL, response);
  }
  
  @After
  public void tearDown() {
    
    provider.deleteAccount(user1, pw1, provider.performLogin(user1, pw1));
    provider.deleteAccount(user2, pw1, provider.performLogin(user1, pw1));
  }

}
