package com.dlshouwen.core.mail.model;

import java.util.Date;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 邮件
 * @author 大连首闻科技有限公司
 * @version 2013-08-17 12:13:03
 */
public class Mail {

	private String mail_id;

	@Length(min=0, max=200, message="邮件主题长度必须在0-200之间")
	private String title;

	private String content;

	@Length(min=0, max=2, message="发送状态长度必须在0-2之间")
	private String send_status;

	@Length(min=0, max=40, message="发送人长度必须在0-40之间")
	private String sender;
	
	private String sender_name;
	
	private String receiver;
	
	private String receiver_name;

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date send_time;

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date create_time;

	public String getMail_id() {
		return mail_id;
	}

	public void setMail_id(String mail_id) {
		this.mail_id = mail_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSend_status() {
		return send_status;
	}

	public void setSend_status(String send_status) {
		this.send_status = send_status;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}
	
	public String getSender_name() {
		return sender_name;
	}

	public void setSender_name(String sender_name) {
		this.sender_name = sender_name;
	}
	
	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getReceiver_name() {
		return receiver_name;
	}

	public void setReceiver_name(String receiver_name) {
		this.receiver_name = receiver_name;
	}

	public Date getSend_time() {
		return send_time;
	}

	public void setSend_time(Date send_time) {
		this.send_time = send_time;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

}