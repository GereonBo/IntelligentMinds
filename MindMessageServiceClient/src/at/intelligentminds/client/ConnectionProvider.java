package at.intelligentminds.client;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TreeSet;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ConnectionProvider {

  public static final String JSON_MESSAGE_KEY_TEXT = "text";
  public static final String JSON_MESSAGE_KEY_DATE = "creatonDate";
  public static final String JSON_MESSAGE_KEY_RECEIVER = "receiverEmail";
  public static final String JSON_MESSAGE_KEY_SENDER = "senderEmail";
  public static final String JSON_EMAIL = "email";
  public static final String JSON_FIRSTNAME = "firstName";
  public static final String JSON_LASTNAME = "lastName";
  public static final String JSON_ACCOUNTNAME = "accountName";
  public static final String JSON_GENDER = "gender";
  public static final String JSON_ABOUTME = "profileText";
  
  private static ConnectionProvider instance;
  private WebTarget target;
  private String authToken;
  private String userEmail;
  
  public enum RegisterResponse {
    SUCCESS, ERROR, PASSWORD, USER_EXISTS, NAME, MISC_ERROR, EMAIL
  }

  private ConnectionProvider(Class<? extends Feature> feature) {
    ClientConfig config = new ClientConfig();

    Client client = ClientBuilder.newClient(config).register(feature);

    this.target = client.target(getBaseURI()).path("mm");
  }

  private ConnectionProvider() {
    ClientConfig config = new ClientConfig();

    Client client = ClientBuilder.newClient(config);

    this.target = client.target(getBaseURI()).path("mm");
  }

  public static ConnectionProvider getInstance(Class<? extends Feature> feature) {
    if (ConnectionProvider.instance == null) {
      ConnectionProvider.instance = new ConnectionProvider(feature);
    }

    return ConnectionProvider.instance;
  }

  public static ConnectionProvider getInstance() {
    if (ConnectionProvider.instance == null) {
      ConnectionProvider.instance = new ConnectionProvider();
    }

    return ConnectionProvider.instance;
  }

  private static URI getBaseURI() {
    return UriBuilder.fromUri("https://intelligentminds-intelligentminds.rhcloud.com/MindMessagesService").build();
//    return UriBuilder.fromUri("http://localhost:8080/MindMessagesService").build();
    //return UriBuilder.fromUri("http://129.27.229.49:8080/MindMessagesService").build();
  }

  public String performLogin(String email, String password) {

    Form login_form = new Form();
    login_form.param("email", email);
    login_form.param("password", password);

    authToken = this.target.path("userservice").path("login").request().accept(MediaType.TEXT_PLAIN)
        .post(Entity.entity(login_form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), String.class);
    if(!authToken.equals("")) this.userEmail = email;
    
    return authToken;
  }
  
  public Boolean performLogout() {
  return this.performLogout(this.userEmail, this.authToken);
  }
  
  public Boolean performLogout(String email, String authtoken) {

    Form logout_form = new Form();
    logout_form.param("email", email);
    logout_form.param("authtoken", authtoken);

    Boolean result = this.target.path("userservice").path("logout").request().accept(MediaType.TEXT_PLAIN)
        .post(Entity.entity(logout_form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), Boolean.class);
    
    if(result) {
      this.userEmail = "";
    this.authToken = "";
    }
    
    return result;
  }

  public boolean validateLogin() {
    return this.validateLogin(this.authToken);
  }

  public boolean validateLogin(String token) {
    Form validate_form = new Form();
    validate_form.param("token", token);

    boolean isLoggedIn = this.target.path("userservice").path("validate").request().accept(MediaType.TEXT_PLAIN)
        .post(Entity.entity(validate_form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), Boolean.class);

    return isLoggedIn;
  }

  public RegisterResponse register(String email, String password, String gender, String firstname, String lastname) {
    Form register_form = new Form();
    register_form.param("email", email);
    register_form.param("password", password);
    register_form.param("gender", gender);
    register_form.param("firstName", firstname);
    register_form.param("lastName", lastname);

    RegisterResponse response = RegisterResponse.values()[this.target.path("userservice").path("register").request()
        .accept(MediaType.TEXT_PLAIN)
        .post(Entity.entity(register_form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), Integer.class)];

    return response;
  }

  public boolean deleteAccount(String email, String password){
    return this.deleteAccount(email, password, this.authToken);
  }
      
  public boolean deleteAccount(String email, String password, String authtoken) {
    Form delete_form = new Form();
    delete_form.param("email", email);
    delete_form.param("password", password);
    delete_form.param("authtoken", authtoken);

    boolean response = this.target.path("userservice").path("deleteuser").request().accept(MediaType.TEXT_PLAIN)
        .post(Entity.entity(delete_form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), Boolean.class);

    return response;
  }

  public ArrayList<User> searchAccounts(String searchText) {
    return searchAccounts(searchText, this.authToken);
  }
  
  public ArrayList<User> searchAccounts(String searchText, String authtoken) {
    
    ArrayList<User> userList = new ArrayList<>();
    
    Form search_form = new Form();
    search_form.param("searchText", searchText);
    search_form.param("authtoken", authtoken);

    JSONArray returnList = new JSONArray();
    String response = this.target.path("userservice").path("searchaccount").request().accept(MediaType.TEXT_PLAIN)
        .post(Entity.entity(search_form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), String.class);

    if (response != null && response != "") {
      returnList = new JSONArray(response);
    }
    
    for(int i = 0; i <  returnList.length(); i++) {
      JSONObject messageObject = returnList.getJSONObject(i);
      if (messageObject.has(JSON_EMAIL) && messageObject.has(JSON_FIRSTNAME)
          && messageObject.has(JSON_LASTNAME)) {
    	try {
    	  String email = messageObject.getString(JSON_EMAIL);
    	  String firstName = messageObject.getString(JSON_FIRSTNAME);
          String lastName = messageObject.getString(JSON_LASTNAME);
          userList.add(new User(firstName,lastName,email));
        }
        catch (JSONException e) {
          System.err.println("Malformed JSON response " + e.getMessage());
        }
      }
    }
    
    return userList;
  }

  public Boolean sendMessage(String receiverEmail, String text) {
    return sendMessage(this.userEmail, receiverEmail, text, this.authToken);
  }
  
  public Boolean sendMessage(String receiverEmail, String text, String authtoken) {
    return sendMessage(this.userEmail, receiverEmail, text, authtoken);
  }
  
  public Boolean sendMessage(String senderEmail, String receiverEmail, String text, String authtoken) {
    Form create_form = new Form();
    create_form.param("senderEmail", senderEmail);
    create_form.param("receiverEmail", receiverEmail);
    create_form.param("text", text);
    create_form.param("authtoken", authtoken);

    Boolean response = this.target.path("messageservice").path("createmessage").request().accept(MediaType.TEXT_PLAIN)
        .post(Entity.entity(create_form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), Boolean.class);

    return response;
  }
  
  public TreeSet<Message> getMessagesBySenderAndReceiverSorted(String receiverEmail) {
    return getMessagesBySenderAndReceiverSorted(this.userEmail, receiverEmail, this.authToken);
  }
  
  public TreeSet<Message> getMessagesBySenderAndReceiverSorted(String requesterEmail, String receiverEmail, String authtoken) {
    TreeSet<Message> ret = new TreeSet<Message>();
    JSONArray array = getMessagesBySenderAndReceiver(requesterEmail, receiverEmail, authtoken);
    
    for(int i = 0; i <  array.length(); i++){
      JSONObject messageObject = array.getJSONObject(i);
      if (messageObject.has(JSON_MESSAGE_KEY_DATE) && messageObject.has(JSON_MESSAGE_KEY_RECEIVER)
          && messageObject.has(JSON_MESSAGE_KEY_SENDER) && messageObject.has(JSON_MESSAGE_KEY_TEXT)) {
        String datestring = messageObject.getString(JSON_MESSAGE_KEY_DATE);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        try {
          ret.add(new Message(format.parse(datestring), 
              messageObject.getString(JSON_MESSAGE_KEY_TEXT),
              messageObject.getString(JSON_MESSAGE_KEY_RECEIVER),
              messageObject.getString(JSON_MESSAGE_KEY_SENDER)));
        }
        catch (JSONException e) {
          System.err.println("Malformed JSON response " + e.getMessage());
        }
        catch (ParseException e) {
          System.err.println("Malformed creation date in JSON response " + e.getMessage());
        }
      }
    }
    return ret;
  }
  
  public JSONArray getMessagesBySenderAndReceiver(String receiverEmail) {
    return getMessagesBySenderAndReceiver(this.userEmail, receiverEmail, this.authToken);
  }
  
  public JSONArray getMessagesBySenderAndReceiver(String receiverEmail, String authtoken) {
    return getMessagesBySenderAndReceiver(this.userEmail, receiverEmail, authtoken);
  }
  
  public JSONArray getMessagesBySenderAndReceiver(String requesterEmail, String receiverEmail, String authtoken) {
    Form retrieve_form = new Form();
    retrieve_form.param("senderEmail", requesterEmail);
    retrieve_form.param("receiverEmail", receiverEmail);
    retrieve_form.param("authtoken", authtoken);

    JSONArray returnList = new JSONArray();
    String response = this.target.path("messageservice").path("retrievemessages").request().accept(MediaType.TEXT_PLAIN)
        .post(Entity.entity(retrieve_form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), String.class);

    if (response != null && response != "") {
      returnList = new JSONArray(response);
    }
    
    return returnList;
  }
  
  public String whoAmI(){
    return userEmail;
  }
  
  public Boolean addContact(String userEmail, String contactEmail, String authtoken) {
    Form add_form = new Form();
    add_form.param("userEmail", userEmail);
    add_form.param("contactEmail", contactEmail);
    add_form.param("authtoken", authtoken);

    Boolean response = this.target.path("userservice").path("addcontact").request().accept(MediaType.TEXT_PLAIN)
        .post(Entity.entity(add_form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), Boolean.class);
    
    return response;
  }
  
  public Boolean addContact(String contactEmail) {
    return addContact(userEmail, contactEmail, authToken);
  }
  
  public ArrayList<User>  getContacts() {
    return getContacts(userEmail, authToken);
  }
  
  public ArrayList<User>  getContacts(String userEmail, String authtoken) {
    Form retrieve_form = new Form();
    ArrayList<User> userList = new ArrayList<>();
    
    retrieve_form.param("userEmail", userEmail);
    retrieve_form.param("authtoken", authtoken);

    JSONArray returnList = new JSONArray();
    String response = this.target.path("userservice").path("retrievecontacts").request().accept(MediaType.TEXT_PLAIN)
        .post(Entity.entity(retrieve_form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), String.class);

    if (response != null && response != "") {
      returnList = new JSONArray(response);
    }
    
    for(int i = 0; i <  returnList.length(); i++) {
      JSONObject messageObject = returnList.getJSONObject(i);
      if (messageObject.has(JSON_EMAIL) && messageObject.has(JSON_FIRSTNAME)
          && messageObject.has(JSON_LASTNAME)) {
      try {
        String email = messageObject.getString(JSON_EMAIL);
        String firstName = messageObject.getString(JSON_FIRSTNAME);
          String lastName = messageObject.getString(JSON_LASTNAME);
          userList.add(new User(firstName,lastName,email));
        }
        catch (JSONException e) {
          System.err.println("Malformed JSON response " + e.getMessage());
        }
      }
    }
    
    return userList;
  }
  
  public Boolean updateUser(String userEmail, String authtoken, String firstName, String lastName, 
		  String profileText) {
    Form update_form = new Form();
    update_form.param("userEmail", userEmail);
    update_form.param("firstName", firstName);
    update_form.param("lastName", lastName);
    update_form.param("profileText", profileText);
    update_form.param("authtoken", authtoken);

    Boolean response = this.target.path("userservice").path("updateuser").request().accept(MediaType.TEXT_PLAIN)
        .post(Entity.entity(update_form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), Boolean.class);
    
    return response;
  }
  
  public Boolean updateUser(String firstName, String lastName, String profileText) {
    return updateUser(userEmail, authToken, firstName, lastName, profileText);
  }
  
  public User getUserInformation(String contactEmail, String authtoken) {
    Form user_form = new Form();
    user_form.param("contactEmail", contactEmail);
    user_form.param("authtoken", authtoken);

    JSONObject userObject = new JSONObject();
    
    String response = this.target.path("userservice").path("userinformation").request().accept(MediaType.TEXT_PLAIN)
        .post(Entity.entity(user_form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), String.class);
    
    if (response != null && response != "") {
    	userObject = new JSONObject(response);
    }
    else {
    	return null;
    }
    
    User user = new User();
    
    if (userObject.has(JSON_EMAIL) && userObject.has(JSON_FIRSTNAME)
            && userObject.has(JSON_LASTNAME) && userObject.has(JSON_ABOUTME) 
            && userObject.has(JSON_ACCOUNTNAME) && userObject.has(JSON_GENDER)) {
      try {
        user.setEmail(userObject.getString(JSON_EMAIL));
        user.setFirstName(userObject.getString(JSON_FIRSTNAME));
        user.setLastName(userObject.getString(JSON_LASTNAME));
        user.setAboutMe(userObject.getString(JSON_ABOUTME));
        user.setAccountName(userObject.getString(JSON_ACCOUNTNAME));
        user.setGender(userObject.getString(JSON_GENDER));
      }
      catch (JSONException e) {
        System.err.println("Malformed JSON response " + e.getMessage());
        return null;
      }
      
      return user;
    }
    
    return null;
  }
  
  public User getUserInformation(String contactEmail) {
    return getUserInformation(contactEmail, authToken);
  }
  
  public User getUserInformation() {
    return getUserInformation(userEmail, authToken);
  }
}
