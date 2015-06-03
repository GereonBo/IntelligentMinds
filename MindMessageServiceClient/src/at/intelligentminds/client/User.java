
package at.intelligentminds.client;


public class User {

  public String firstName = null;
  public String lastName = null;
  public String email = null;
  public String accountName = null;
  public int age = -1;
  public String country = null;
  public String zipCode = null;
  public String location = null;
  public String address = null;
  public String aboutMe = null;

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
  
}
