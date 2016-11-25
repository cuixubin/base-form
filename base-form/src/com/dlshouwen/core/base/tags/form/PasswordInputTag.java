package com.dlshouwen.core.base.tags.form;

import javax.servlet.jsp.JspException;

import org.springframework.web.servlet.tags.form.TagWriter;

/**
 * 基于Spring MVC扩展PasswordInput标签
 * @author 大连首闻科技有限公司
 * @version 2015-12-2 14:12:41
 */
@SuppressWarnings("serial")
public class PasswordInputTag extends org.springframework.web.servlet.tags.form.PasswordInputTag {

	public static final String VALID_ATTRIBUTE = "valid";

	public static final String VALIDTITLE_ATTRIBUTE = "validTitle";
	
	public static final String VALIDERRORCSS_ATTRIBUTE = "validErrorCss";
	
	public static final String VALIDINFOAREA_ATTRIBUTE = "validInfoArea";
	
	public static final String VALIDPASSWORD_ATTRIBUTE = "validPassword";
	

	private String valid;
	
	private String validTitle;
	
	private String validErrorCss;
	
	private String validInfoArea;
	
	private String validPassword;


	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	public String getValidTitle() {
		return validTitle;
	}

	public void setValidTitle(String validTitle) {
		this.validTitle = validTitle;
	}

	public String getValidErrorCss() {
		return validErrorCss;
	}

	public void setValidErrorCss(String validErrorCss) {
		this.validErrorCss = validErrorCss;
	}

	public String getValidInfoArea() {
		return validInfoArea;
	}

	public void setValidInfoArea(String validInfoArea) {
		this.validInfoArea = validInfoArea;
	}
	
	public String getValidPassword() {
		return validPassword;
	}

	public void setValidPassword(String validPassword) {
		this.validPassword = validPassword;
	}

	protected void writeValue(TagWriter tagWriter) throws JspException {
		super.writeValue(tagWriter);
		
		writeOptionalAttribute(tagWriter, VALID_ATTRIBUTE, getValid());
		writeOptionalAttribute(tagWriter, VALIDTITLE_ATTRIBUTE, getValidTitle());
		writeOptionalAttribute(tagWriter, VALIDERRORCSS_ATTRIBUTE, getValidErrorCss());
		writeOptionalAttribute(tagWriter, VALIDINFOAREA_ATTRIBUTE, getValidInfoArea());
		writeOptionalAttribute(tagWriter, VALIDPASSWORD_ATTRIBUTE, getValidPassword());
	}

}
