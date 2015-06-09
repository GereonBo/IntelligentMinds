
package at.intelligentminds.client;


public class User {

  private String firstName = null;
  private String lastName = null;
  private String email = null;
  private String accountName = null;
  private int age = -1;
  private String country = null;
  private String zipCode = null;
  private String location = null;
  private String address = null;
  private String aboutMe = null;
  private String gender = null;

  public User(String firstName,String lastName,String email) {
    super();
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }

  public User(String firstName,String lastName,String email,   
   String accountName, int age, String country, String zipCode,
   String location, String address, String aboutMe) {
    super();
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.accountName = accountName;
    this.age = age;
    this.country = country;
    this.zipCode = zipCode;
    this.location = location;
    this.address = address;
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

  public int getAge() {
    return age;
  }

  public String getCountry() {
    return country;
  }

  public String getZipCode() {
    return zipCode;
  }

  public String getLocation() {
    return location;
  }

  public String getAddress() {
    return address;
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
