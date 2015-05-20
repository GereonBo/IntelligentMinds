package at.intelligentminds.service.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "group", catalog = "mindmessages")
public class Group implements java.io.Serializable {

  private static final long serialVersionUID = 2751247612846123166L;
  private int id;
  private String name;
  private Set<User> users = new HashSet<User>(0);
  private Set<Message> messages = new HashSet<Message>(0);

  public Group() {
  }

  public Group(int id) {
    this.id = id;
  }

  public Group(int id, String name, Set<User> users, Set<Message> messages) {
    this.id = id;
    this.name = name;
    this.users = users;
    this.messages = messages;
  }

  @Id
  @Column(name = "id", unique = true, nullable = false)
  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Column(name = "name", length = 45)
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "user_group", catalog = "mindmessages", joinColumns = { @JoinColumn(name = "group_id",
      nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "user_id", nullable = false,
      updatable = false) })
  public Set<User> getUsers() {
    return this.users;
  }

  public void setUsers(Set<User> users) {
    this.users = users;
  }

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "group")
  public Set<Message> getMessages() {
    return this.messages;
  }

  public void setMessages(Set<Message> messages) {
    this.messages = messages;
  }
}
