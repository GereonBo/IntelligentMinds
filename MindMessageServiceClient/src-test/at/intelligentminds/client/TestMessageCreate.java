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
    provider.register(userEmail2, pw1, "male", "usercheck", "mustermann");

    authTokenUser1 = provider.performLogin(userEmail1, pw1);
    authTokenUser2 = provider.performLogin(userEmail2, pw1);
  }

  @After
  public void tearDown() throws Exception {
    provider.deleteAccount(userEmail1, pw1, authTokenUser1);
    provider.deleteAccount(userEmail2, pw1, authTokenUser2);
  }

  @Test
  public void testCreateMessage() {
    Boolean response = provider.sendMessage(userEmail1, userEmail2, "hallo", authTokenUser1);
    Boolean response2 = provider.sendMessage(userEmail2, userEmail1, "hey", authTokenUser2);

    assertTrue(response);
    assertTrue(response2);
  }

  @Test
  public void testNotLoggedIn() {
    Boolean response = provider.sendMessage(userEmail1, userEmail2, "textblabla", "");
    assertFalse(response);
  }

  @Test
  public void testReceiverNotExists() {
    Boolean response = provider.sendMessage(userEmail1, "ghost", "textblabla", "");
    assertFalse(response);
  }

  @Test
  public void testSenderNotExists() {
    Boolean response = provider.sendMessage("ghostsender", userEmail1, "textblabla", "");
    assertFalse(response);
  }
}
