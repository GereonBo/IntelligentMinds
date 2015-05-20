package at.intelligentminds.client;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestMessageCreate {

  private ConnectionProvider provider;

  private String userEmail1;
  private String authTokenUser1;
  private String authTokenUser2;
  private String userEmail2;

  private String pw1;

  @Before
  public void setUp() throws Exception {
    userEmail1 = "usermustermann@bla.com";
    userEmail2 = "user0816@bla.com";
    pw1 = "Passw0rd";

    provider = ConnectionProvider.getInstance();

    provider.register(userEmail1, pw1, "male", "user", "mustermann");
    provider.register(userEmail2, pw1, "male", "user2", "mustermann");

    authTokenUser1 = provider.performLogin(userEmail1, pw1);
    authTokenUser2 = provider.performLogin(userEmail2, pw1);
  }

  @After
  public void tearDown() throws Exception {
    provider.deleteAccount(userEmail1, pw1, authTokenUser1);
    provider.deleteAccount(userEmail2, pw1, authTokenUser2);
  }

  @Test
  public void test() {
    Boolean response = provider.sendMessage(userEmail1, userEmail2, "textblabla", authTokenUser1);
    assertTrue(response);
  }

}
