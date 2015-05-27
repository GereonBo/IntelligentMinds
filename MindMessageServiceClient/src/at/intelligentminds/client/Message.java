package at.intelligentminds.client;

import java.util.Date;

public class Message implements Comparable<Message>{

  public Date date;
  public String text;
  public String receiverEmail;
  public String senderEmail;
  
  public Message(Date date, String text, String receiverEmail, String senderEmail) {
    super();
    this.date = date;
    this.text = text;
    this.receiverEmail = receiverEmail;
    this.senderEmail = senderEmail;
  }

  @Override
  public int compareTo(Message o) {
    return this.date.compareTo(o.date);
  }
}
