package com.dlshouwen.core.base.utils;

import java.util.List;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;

public class SimpleMailSender
{
  private Properties props = System.getProperties();
  private transient MailAuthenticator authenticator;
  private transient Session session;
  
  public SimpleMailSender(String smtpHostName, String username, String password)
  {
    init(username, password, smtpHostName);
  }
  
  public SimpleMailSender(String username, String password)
  {
    String smtpHostName = "smtp." + username.split("@")[1];
    init(username, password, smtpHostName);
  }
  
  private void init(String username, String password, String smtpHostName)
  {
    this.props.put("mail.smtp.auth", "true");
    this.props.put("mail.smtp.host", smtpHostName);
    

    this.authenticator = new MailAuthenticator(username, password);
    

    this.session = Session.getInstance(this.props, this.authenticator);
  }
  
  public void send(String recipient, String subject, String content)
    throws AddressException, MessagingException
  {
    MimeMessage message = new MimeMessage(this.session);
    

    message.setFrom(new InternetAddress(this.authenticator.getUsername()));
    

    message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(recipient));
    

    message.setSubject(subject);
    

    Multipart mp = new MimeMultipart("related");
    MimeBodyPart mbp = new MimeBodyPart();
    mbp.setContent(content.toString(), "text/html;charset=utf-8");
    mp.addBodyPart(mbp);
    message.setContent(mp);
    




    Transport.send(message);
  }
  
  public void send(List<String> recipients, String subject, String content)
    throws AddressException, MessagingException
  {
    MimeMessage message = new MimeMessage(this.session);
    

    message.setFrom(new InternetAddress(this.authenticator.getUsername()));
    

    int num = recipients.size();
    InternetAddress[] addresses = new InternetAddress[num];
    for (int i = 0; i < num; i++) {
      addresses[i] = new InternetAddress((String)recipients.get(i));
    }
    message.setRecipients(MimeMessage.RecipientType.TO, addresses);
    

    message.setSubject(subject);
    

    message.setContent(content.toString(), "text/html;charset=utf-8");
    

    Transport.send(message);
  }
  
  public void send(String recipient, SimpleMail mail)
    throws AddressException, MessagingException
  {
    send(recipient, mail.getSubject(), mail.getContent());
  }
  
  public void send(List<String> recipients, SimpleMail mail)
    throws AddressException, MessagingException
  {
    send(recipients, mail.getSubject(), mail.getContent());
  }
}
