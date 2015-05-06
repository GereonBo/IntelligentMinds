package at.intelligentminds.service.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "request", catalog = "mindmessages")
public class Request implements java.io.Serializable {

  private static final long serialVersionUID = 2751247612846123166L;

  private int id;
  
  private User confirmer;
  
  private User requester;

  private Status status;

  public Request() {
  }

  public Request(int id, User confirmer, User requester, Status status) {
    this.id = id;
    this.confirmer = confirmer;
    this.requester = requester;
    this.status = status;
  }

  public enum Status {
    Pending, Accepted, Declined
  }

  public Request(int id, Status status) {
    this.id = id;
    this.status = status;
  }

  @Id
  @Column(name = "id", unique = true, nullable = false)
  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "confirmer_id")
  public User getConfirmer() {
    return this.confirmer;
  }

  public void setConfirmer(User confirmer) {
    this.confirmer = confirmer;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "requester_id")
  public User getRequester() {
    return this.requester;
  }

  public void setRequester(User requester) {
    this.requester = requester;
  }

  @Column(name = "status", unique = false, nullable = false)
  @Enumerated(EnumType.ORDINAL)
  public Status getStatus() {
    return this.status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

}
