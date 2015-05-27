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
    int ret =  this.date.compareTo(o.date);
    if(ret!=0) return ret;
    ret = this.receiverEmail.compareTo(o.receiverEmail);
    if(ret!=0) return ret;
    ret = this.senderEmail.compareTo(o.senderEmail);
    if(ret!=0) return ret;
    ret = this.text.compareTo(o.text);
    return ret;
  }
}
