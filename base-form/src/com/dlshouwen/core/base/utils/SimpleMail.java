package com.dlshouwen.core.base.utils;

public class SimpleMail
{
  private String subject;
  private String content;
  
  public void setSubject(String subject)
  {
    this.subject = subject;
  }
  
  public void setContent(String content)
  {
    this.content = content;
  }
  
  public String getSubject()
  {
    return this.subject;
  }
  
  public String getContent()
  {
    return this.content;
  }
}
