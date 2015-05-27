package at.intelligentminds.service.model;

// Generated 29-Apr-2015 15:07:52 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name = "message", catalog = "mindmessages")
public class Message implements java.io.Serializable {

  private static final long serialVersionUID = -6693776907265907711L;
  private int id;
  private User userByUserSenderId;
  private Group group;
  private User userByUserReceiverId;
  private Date creatonDate;
  private String text;

  public Message() {
  }

  public Message(int id) {
    this.id = id;
  }

  public Message(int id, User userByUserSenderId, Group group, User userByUserReceiverId, Date creatonDate, String text) {
    this.id = id;
    this.userByUserSenderId = userByUserSenderId;
    this.group = group;
    this.userByUserReceiverId = userByUserReceiverId;
    this.creatonDate = creatonDate;
    this.text = text;
  }

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  @Column(name = "id", unique = true, nullable = false)
  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_sender_id")
  public User getUserByUserSenderId() {
    return this.userByUserSenderId;
  }

  public void setUserByUserSenderId(User userByUserSenderId) {
    this.userByUserSenderId = userByUserSenderId;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "group_receiver_id")
  public Group getGroup() {
    return this.group;
  }

  public void setGroup(Group group) {
    this.group = group;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_receiver_id")
  public User getUserByUserReceiverId() {
    return this.userByUserReceiverId;
  }

  public void setUserByUserReceiverId(User userByUserReceiverId) {
    this.userByUserReceiverId = userByUserReceiverId;
  }

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "creaton_date", length = 10)
  public Date getCreatonDate() {
    return this.creatonDate;
  }

  public void setCreatonDate(Date creatonDate) {
    this.creatonDate = creatonDate;
  }

  @Column(name = "text", length = 300)
  public String getText() {
    return this.text;
  }

  public void setText(String text) {
    this.text = text;
  }

}
