package com.dlshouwen.core.mail.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 邮件接收
 * @author 大连首闻科技有限公司
 * @version 2013-08-17 12:13:03
 */
public class MailReceive {

	private String mail_id;

	private String receiver;

	@NotBlank(message="接收状态不能为空")
	@Length(min=0, max=2, message="接收状态长度必须在0-2之间")
	private String receive_status;

	public String getMail_id() {
		return mail_id;
	}

	public void setMail_id(String mail_id) {
		this.mail_id = mail_id;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getReceive_status() {
		return receive_status;
	}

	public void setReceive_status(String receive_status) {
		this.receive_status = receive_status;
	}

}