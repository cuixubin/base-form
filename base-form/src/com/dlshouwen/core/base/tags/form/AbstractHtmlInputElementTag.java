package com.dlshouwen.core.base.tags.form;

import javax.servlet.jsp.JspException;

@SuppressWarnings("serial")
public abstract class AbstractHtmlInputElementTag extends AbstractHtmlElementTag {

	public static final String ONFOCUS_ATTRIBUTE = "onfocus";

	public static final String ONBLUR_ATTRIBUTE = "onblur";

	public static final String ONCHANGE_ATTRIBUTE = "onchange";

	public static final String ACCESSKEY_ATTRIBUTE = "accesskey";

	public static final String DISABLED_ATTRIBUTE = "disabled";

	public static final String READONLY_ATTRIBUTE = "readonly";


	private String onfocus;

	private String onblur;

	private String onchange;

	private String accesskey;

	private boolean disabled;

	private boolean readonly;


	public void setOnfocus(String onfocus) {
		this.onfocus = onfocus;
	}

	protected String getOnfocus() {
		return this.onfocus;
	}

	public void setOnblur(String onblur) {
		this.onblur = onblur;
	}

	protected String getOnblur() {
		return this.onblur;
	}

	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}

	protected String getOnchange() {
		return this.onchange;
	}

	public void setAccesskey(String accesskey) {
		this.accesskey = accesskey;
	}

	protected String getAccesskey() {
		return this.accesskey;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	protected boolean isDisabled() {
		return this.disabled;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	protected boolean isReadonly() {
		return this.readonly;
	}

	@Override
	protected void writeOptionalAttributes(TagWriter tagWriter) throws JspException {
		super.writeOptionalAttributes(tagWriter);

		writeOptionalAttribute(tagWriter, ONFOCUS_ATTRIBUTE, getOnfocus());
		writeOptionalAttribute(tagWriter, ONBLUR_ATTRIBUTE, getOnblur());
		writeOptionalAttribute(tagWriter, ONCHANGE_ATTRIBUTE, getOnchange());
		writeOptionalAttribute(tagWriter, ACCESSKEY_ATTRIBUTE, getAccesskey());
		if (isDisabled()) {
			tagWriter.writeAttribute(DISABLED_ATTRIBUTE, "disabled");
		}
		if (isReadonly()) {
			writeOptionalAttribute(tagWriter, READONLY_ATTRIBUTE, "readonly");
		}
	}

}
