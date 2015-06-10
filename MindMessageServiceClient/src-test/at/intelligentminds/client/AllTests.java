package at.intelligentminds.client;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import junit.framework.TestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
   TestAddContact.class,
   TestGetContacts.class,
   TestGetUserInformation.class,
   TestLogin.class,
   TestLogout.class,
   TestMessageCreate.class,
   TestMessageRetrieve.class,
   TestRegister.class,
   TestSearch.class,
   TestUpdateUserInformation.class
})
public class AllTests extends TestSuite{

 
}
