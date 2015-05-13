package at.intelligentminds.service.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity()
@Table(name="user")
public class User {

  private String email;
  private String firstName;
  private String lastName;
  private String pwHash;
  private String profileText;
  private String gender;
  private Set<User> usersForContactId = new HashSet<User>(0);
  private Set<Message> messagesForUserReceiverId = new HashSet<Message>(0);
  private Set<User> usersForUserId = new HashSet<User>(0);
  private Set<Message> messagesForUserSenderId = new HashSet<Message>(0);
  private Set<Group> groups = new HashSet<Group>(0);
  
  @OneToMany(mappedBy="confirmer")
  private Set<Request> confirmerRequests = new HashSet<Request>(0);
  
  @OneToMany(mappedBy="requester")
  private Set<Request> requesterRequests = new HashSet<Request>(0);
  
  public User()
  {
    
  }
  
  public User(String email) {
    this.email = email;
  }
  
  public User(String email, String firstName, String lastName, String pwHash, String profileText, String gender,
      Set<User> usersForContactId, Set<Message> messagesForUserReceiverId, Set<User> usersForUserId,
      Set<Message> messagesForUserSenderId, Set<Group> groups, Set<Request> userRequests, Set<Request> requesterRequests) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.pwHash = pwHash;
    this.email = email;
    this.profileText = profileText;
    this.gender = gender;
    this.usersForContactId = usersForContactId;
    this.messagesForUserReceiverId = messagesForUserReceiverId;
    this.usersForUserId = usersForUserId;
    this.messagesForUserSenderId = messagesForUserSenderId;
    this.groups = groups;
    this.confirmerRequests = userRequests;
    this.requesterRequests = requesterRequests;
  }

  @Id
  @Column(name="email", nullable=false)
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Column(name="firstName")
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @Column(name="lastName")
  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @Column(name="pwHash")
  public String getPwHash() {
    return pwHash;
  }

  public void setPwHash(String pwHash) {
    this.pwHash = pwHash;
  }
  
  @Column(name = "profile_text", length = 256)
  public String getProfileText() {
    return this.profileText;
  }

  public void setProfileText(String profileText) {
    this.profileText = profileText;
  }
  
  @Column(name = "gender", length = 6)
  public String getGender() {
    return this.profileText;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "user_contact", catalog = "mindmessages", joinColumns = { @JoinColumn(name = "user_id",
      nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "contact_id", nullable = false,
      updatable = false) })
  public Set<User> getUsersForContactId() {
    return this.usersForContactId;
  }

  public void setUsersForContactId(Set<User> usersForContactId) {
    this.usersForContactId = usersForContactId;
  }

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "userByUserReceiverId")
  public Set<Message> getMessagesForUserReceiverId() {
    return this.messagesForUserReceiverId;
  }

  public void setMessagesForUserReceiverId(Set<Message> messagesForUserReceiverId) {
    this.messagesForUserReceiverId = messagesForUserReceiverId;
  }

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "user_contact", catalog = "mindmessages", joinColumns = { @JoinColumn(name = "contact_id",
      nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "user_id", nullable = false,
      updatable = false) })
  public Set<User> getUsersForUserId() {
    return this.usersForUserId;
  }

  public void setUsersForUserId(Set<User> usersForUserId) {
    this.usersForUserId = usersForUserId;
  }

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "userByUserSenderId")
  public Set<Message> getMessagesForUserSenderId() {
    return this.messagesForUserSenderId;
  }

  public void setMessagesForUserSenderId(Set<Message> messagesForUserSenderId) {
    this.messagesForUserSenderId = messagesForUserSenderId;
  }

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "user_group", catalog = "mindmessages", joinColumns = { @JoinColumn(name = "user_id",
      nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "group_id", nullable = false,
      updatable = false) })
  public Set<Group> getGroups() {
    return this.groups;
  }

  public void setGroups(Set<Group> groups) {
    this.groups = groups;
  } 
}
