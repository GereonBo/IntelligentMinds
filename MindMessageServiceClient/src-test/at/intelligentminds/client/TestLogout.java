package at.intelligentminds.client;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestLogout {

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

	@After
	public void tearDown() throws Exception {
		provider.deleteAccount(userEmail1, pw, provider.performLogin(userEmail1, pw));
	    provider.deleteAccount(userEmail2, pw, provider.performLogin(userEmail2, pw));
	}

	@Test
	public void testLogout() {
		String authToken = provider.performLogin(userEmail1, pw);	    
	    assertNotNull(authToken);
	    assertNotEquals("", authToken);
	    
	    Boolean logoutSucceeded = provider.performLogout(userEmail1, authToken);
	    assertTrue(logoutSucceeded);	    
	}
	
	@Test
	public void testAlreadyLoggedOut() {
		String authToken = provider.performLogin(userEmail1, pw);	    
	    assertNotNull(authToken);
	    assertNotEquals("", authToken);
	    
	    Boolean logoutSucceeded = provider.performLogout(userEmail1, authToken);
	    assertTrue(logoutSucceeded);
	    
	    Boolean response = provider.sendMessage(userEmail1, userEmail2, "fail", authToken);
	    assertFalse(response);
	}
	
	@Test
	public void testNotLoggedIn() {    	    
	    Boolean logoutSucceeded = provider.performLogout(userEmail1, "");
	    assertFalse(logoutSucceeded);	    
	}

}
