package com.dlshouwen.core.base.tags.form;

import javax.servlet.jsp.JspException;

@SuppressWarnings("serial")
public class InputTag extends AbstractHtmlInputElementTag {

	public static final String SIZE_ATTRIBUTE = "size";

	public static final String MAXLENGTH_ATTRIBUTE = "maxlength";

	public static final String ALT_ATTRIBUTE = "alt";

	public static final String ONSELECT_ATTRIBUTE = "onselect";

	public static final String READONLY_ATTRIBUTE = "readonly";

	public static final String AUTOCOMPLETE_ATTRIBUTE = "autocomplete";
	
	public static final String VALID_ATTRIBUTE = "valid";

	public static final String VALIDTITLE_ATTRIBUTE = "validTitle";
	
	public static final String VALIDERRORCSS_ATTRIBUTE = "validErrorCss";
	
	public static final String VALIDINFOAREA_ATTRIBUTE = "validInfoArea";
	
	public static final String VALIDUNIQUE_ATTRIBUTE = "validUnique";
	
	
	private String valid;
	
	private String validTitle;
	
	private String validErrorCss;
	
	private String validInfoArea;
	
	private String validUnique;
	

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
	
	public String getValidUnique() {
		return validUnique;
	}

	public void setValidUnique(String validUnique) {
		this.validUnique = validUnique;
	}


	private String size;

	private String maxlength;

	private String alt;

	private String onselect;

	private String autocomplete;


	public void setSize(String size) {
		this.size = size;
	}

	protected String getSize() {
		return this.size;
	}

	public void setMaxlength(String maxlength) {
		this.maxlength = maxlength;
	}

	protected String getMaxlength() {
		return this.maxlength;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	protected String getAlt() {
		return this.alt;
	}

	public void setOnselect(String onselect) {
		this.onselect = onselect;
	}

	protected String getOnselect() {
		return this.onselect;
	}

	public void setAutocomplete(String autocomplete) {
		this.autocomplete = autocomplete;
	}

	protected String getAutocomplete() {
		return this.autocomplete;
	}

	@Override
	protected int writeTagContent(TagWriter tagWriter) throws JspException {
		tagWriter.startTag("input");

		writeDefaultAttributes(tagWriter);
		if (!hasDynamicTypeAttribute()) {
			tagWriter.writeAttribute("type", getType());
		}
		writeValue(tagWriter);

		writeOptionalAttribute(tagWriter, SIZE_ATTRIBUTE, getSize());
		writeOptionalAttribute(tagWriter, MAXLENGTH_ATTRIBUTE, getMaxlength());
		writeOptionalAttribute(tagWriter, ALT_ATTRIBUTE, getAlt());
		writeOptionalAttribute(tagWriter, ONSELECT_ATTRIBUTE, getOnselect());
		writeOptionalAttribute(tagWriter, AUTOCOMPLETE_ATTRIBUTE, getAutocomplete());

		tagWriter.endTag();
		return SKIP_BODY;
	}

	private boolean hasDynamicTypeAttribute() {
		return getDynamicAttributes() != null && getDynamicAttributes().containsKey("type");
	}

	protected void writeValue(TagWriter tagWriter) throws JspException {
		String value = getDisplayString(getBoundValue(), getPropertyEditor());
		String type = hasDynamicTypeAttribute() ? (String) getDynamicAttributes().get("type") : getType();
		tagWriter.writeAttribute("value", processFieldValue(getName(), value, type));
		
		writeOptionalAttribute(tagWriter, VALID_ATTRIBUTE, getValid());
		writeOptionalAttribute(tagWriter, VALIDTITLE_ATTRIBUTE, getValidTitle());
		writeOptionalAttribute(tagWriter, VALIDERRORCSS_ATTRIBUTE, getValidErrorCss());
		writeOptionalAttribute(tagWriter, VALIDINFOAREA_ATTRIBUTE, getValidInfoArea());
		writeOptionalAttribute(tagWriter, VALIDUNIQUE_ATTRIBUTE, getValidUnique());
	}

	@Override
	protected boolean isValidDynamicAttribute(String localName, Object value) {
		if ("type".equals(localName)) {
			if ("checkbox".equals(value) || "radio".equals(value)) {
				return false;
			}
		}
		return true;
	}

	protected String getType() {
		return "text";
	}

}
