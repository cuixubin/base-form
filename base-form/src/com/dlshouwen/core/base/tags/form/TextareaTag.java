package com.dlshouwen.core.base.tags.form;

import javax.servlet.jsp.JspException;

@SuppressWarnings("serial")
public class TextareaTag extends AbstractHtmlInputElementTag {

	public static final String ROWS_ATTRIBUTE = "rows";

	public static final String COLS_ATTRIBUTE = "cols";

	public static final String ONSELECT_ATTRIBUTE = "onselect";

	public static final String READONLY_ATTRIBUTE = "readonly";
	
	public static final String VALID_ATTRIBUTE = "valid";

	public static final String VALIDTITLE_ATTRIBUTE = "validTitle";
	
	public static final String VALIDERRORCSS_ATTRIBUTE = "validErrorCss";
	
	public static final String VALIDINFOAREA_ATTRIBUTE = "validInfoArea";


	private String rows;

	private String cols;

	private String onselect;
	
	private String valid;
	
	private String validTitle;
	
	private String validErrorCss;
	
	private String validInfoArea;

	public void setRows(String rows) {
		this.rows = rows;
	}

	protected String getRows() {
		return this.rows;
	}

	public void setCols(String cols) {
		this.cols = cols;
	}

	protected String getCols() {
		return this.cols;
	}

	public void setOnselect(String onselect) {
		this.onselect = onselect;
	}

	protected String getOnselect() {
		return this.onselect;
	}
	
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

	@Override
	protected int writeTagContent(TagWriter tagWriter) throws JspException {
		tagWriter.startTag("textarea");
		writeDefaultAttributes(tagWriter);
		writeOptionalAttribute(tagWriter, ROWS_ATTRIBUTE, getRows());
		writeOptionalAttribute(tagWriter, COLS_ATTRIBUTE, getCols());
		writeOptionalAttribute(tagWriter, ONSELECT_ATTRIBUTE, getOnselect());
		
		writeOptionalAttribute(tagWriter, VALID_ATTRIBUTE, getValid());
		writeOptionalAttribute(tagWriter, VALIDTITLE_ATTRIBUTE, getValidTitle());
		writeOptionalAttribute(tagWriter, VALIDERRORCSS_ATTRIBUTE, getValidErrorCss());
		writeOptionalAttribute(tagWriter, VALIDINFOAREA_ATTRIBUTE, getValidInfoArea());
		
		String value = getDisplayString(getBoundValue(), getPropertyEditor());
		tagWriter.appendValue(processFieldValue(getName(), value, "textarea"));
		tagWriter.endTag();
		return SKIP_BODY;
	}

}
