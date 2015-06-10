
package at.intelligentminds.client;


public class User {

  private String firstName = null;
  private String lastName = null;
  private String email = null;
  private String accountName = null;
  private String aboutMe = null;
  private String gender = null;

  public User(String firstName,String lastName,String email) {
    super();
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }

  public User(String firstName,String lastName,String email,   
   String accountName, String aboutMe) {
    super();
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.accountName = accountName;
    this.aboutMe = aboutMe;
  }
  
  public User() {	  
  }
  
  @Override
  public String toString() {      
      return firstName+" "+lastName+ " - "+email;
  }
  
  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getEmail() {
    return email;
  }

  public String getAccountName() {
    return accountName;
  }

  public String getAboutMe() {
    return aboutMe;
  }

  public void setFirstName(String firstName) {
	this.firstName = firstName;
  }

  public void setLastName(String lastName) {
	this.lastName = lastName;
  }

  public void setEmail(String email) {
	this.email = email;
  }

  public void setAccountName(String accountName) {
	this.accountName = accountName;
  }

  public void setAboutMe(String aboutMe) {
	this.aboutMe = aboutMe;
  }

  public String getGender() {
	return gender;
  }

  public void setGender(String gender) {
	this.gender = gender;
  } 
}
