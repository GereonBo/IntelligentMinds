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
    RegisterResponse response = provider.register(user1, pw1, "male", "user", "mustermann");
    
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
    assertEquals(RegisterResponse.USER_EXISTS, response2);
  }
  
  @Test
  @Ignore
  /**
   * test username already exists
   */   
  public void testRegisterDeleteRegisterWithoutLogin() {
    //Register
	RegisterResponse response = provider.register(user2, pw1, "male", "user", "mustermann");
    assertNotNull(response);
    assertEquals(RegisterResponse.SUCCESS, response);
    //Delete
    provider.deleteAccount(provider.performLogin(user2, pw1));
    //Reregister
    response = provider.register(user2, pw1, "male", "user", "mustermann");
    assertNotEquals(RegisterResponse.USER_EXISTS, response);
  }
  
  @Test
  /**
   * test using weak password
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
  
  @Test
  /**
   * wrong name
   */   
  public void testRegisterNamefail() {
    RegisterResponse response = provider.register(user3, pw1, "male", "12345weird name", "mustermann");
    
    assertNotNull(response);
    assertEquals(RegisterResponse.NAME, response);
  }
  
  @Test
  /**
   * wrong gender
   */   
  public void testRegisterGenderfail() {
    RegisterResponse response = provider.register(user3, pw1, "unknown", "user", "mustermann");
    
    assertNotNull(response);
    assertEquals(RegisterResponse.MISC_ERROR, response);
  }
  
  @After
  public void tearDown() {
    
    provider.deleteAccount(provider.performLogin(user1, pw1));
    provider.deleteAccount(provider.performLogin(user1, pw1));
  }

}
