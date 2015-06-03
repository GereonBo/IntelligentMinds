package at.intelligentminds.service.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name = "user")
public class User {

  private String email;
  private String accountName;
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

  @OneToMany(mappedBy = "confirmer")
  private Set<Request> confirmerRequests = new HashSet<Request>(0);

  @OneToMany(mappedBy = "requester")
  private Set<Request> requesterRequests = new HashSet<Request>(0);

  public User() {

  }

  public User(String email) {
    this.email = email;
  }
  
  public User(String email, String accountName, String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.accountName = accountName;
    this.email = email;
  }

  public User(String email, String accountName, String firstName, String lastName, String pwHash, String profileText,
      String gender, Set<User> usersForContactId, Set<Message> messagesForUserReceiverId, Set<User> usersForUserId,
      Set<Message> messagesForUserSenderId, Set<Group> groups, Set<Request> userRequests, Set<Request> requesterRequests) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.accountName = accountName;
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
  @Column(name = "email", nullable = false)
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
  
  @Column(name = "accountName", length=50)
  public String getAccountName() {
    return this.accountName;
  }

  public void setAccountName(String accountName) {
    this.accountName = accountName;
  }

  @Column(name = "firstName")
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @Column(name = "lastName")
  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @Column(name = "pwHash")
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
    return this.gender;
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

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "userByUserReceiverId", cascade = CascadeType.ALL)
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

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "userByUserSenderId", cascade = CascadeType.ALL)
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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 3;
    result = prime * result + ((accountName == null) ? 0 : accountName.hashCode());
    result = prime * result + ((email == null) ? 0 : email.hashCode());
    result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
    result = prime * result + ((gender == null) ? 0 : gender.hashCode());
    result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    User other = (User) obj;
    if (accountName == null) {
      if (other.accountName != null) {
        return false;
      }
    }
    else if (!accountName.equals(other.accountName)) {
      return false;
    }
    if (email == null) {
      if (other.email != null) {
        return false;
      }
    }
    else if (!email.equals(other.email)) {
      return false;
    }
    if (firstName == null) {
      if (other.firstName != null) {
        return false;
      }
    }
    else if (!firstName.equals(other.firstName)) {
      return false;
    }
    if (gender == null) {
      if (other.gender != null) {
        return false;
      }
    }
    else if (!gender.equals(other.gender)) {
      return false;
    }
    if (lastName == null) {
      if (other.lastName != null) {
        return false;
      }
    }
    else if (!lastName.equals(other.lastName)) {
      return false;
    }
    return true;
  }  
  
}
