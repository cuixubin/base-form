package com.dlshouwen.core.base.tags.form;

import java.util.Collection;
import java.util.Map;
import javax.servlet.jsp.JspException;

import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.servlet.support.BindStatus;

@SuppressWarnings({"serial", "rawtypes"})
public class SelectTag extends AbstractHtmlInputElementTag {

	public static final String LIST_VALUE_PAGE_ATTRIBUTE =
			"org.springframework.web.servlet.tags.form.SelectTag.listValue";
	
	public static final String VALID_ATTRIBUTE = "valid";

	public static final String VALIDTITLE_ATTRIBUTE = "validTitle";
	
	public static final String VALIDERRORCSS_ATTRIBUTE = "validErrorCss";
	
	public static final String VALIDINFOAREA_ATTRIBUTE = "validInfoArea";

	private static final Object EMPTY = new Object();

	private Object items;

	private String itemValue;

	private String itemLabel;

	private String size;
	
	private String valid;
	
	private String validTitle;
	
	private String validErrorCss;
	
	private String validInfoArea;
	
	private String emptyText;

	private Object multiple = Boolean.FALSE;

	private TagWriter tagWriter;

	public void setItems(Object items) {
		this.items = (items != null ? items : EMPTY);
	}

	protected Object getItems() {
		return this.items;
	}

	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	protected String getItemValue() {
		return this.itemValue;
	}

	public void setItemLabel(String itemLabel) {
		this.itemLabel = itemLabel;
	}

	protected String getItemLabel() {
		return this.itemLabel;
	}

	public void setSize(String size) {
		this.size = size;
	}

	protected String getSize() {
		return this.size;
	}

	public void setMultiple(Object multiple) {
		this.multiple = multiple;
	}

	protected Object getMultiple() {
		return this.multiple;
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
	
	public String getEmptyText() {
		return emptyText;
	}

	public void setEmptyText(String emptyText) {
		this.emptyText = emptyText;
	}

	@Override
	protected int writeTagContent(TagWriter tagWriter) throws JspException {
		tagWriter.startTag("select");
		writeDefaultAttributes(tagWriter);
		if (isMultiple()) {
			tagWriter.writeAttribute("multiple", "multiple");
		}
		tagWriter.writeOptionalAttributeValue("size", getDisplayString(evaluate("size", getSize())));
		
		writeOptionalAttribute(tagWriter, VALID_ATTRIBUTE, getValid());
		writeOptionalAttribute(tagWriter, VALIDTITLE_ATTRIBUTE, getValidTitle());
		writeOptionalAttribute(tagWriter, VALIDERRORCSS_ATTRIBUTE, getValidErrorCss());
		writeOptionalAttribute(tagWriter, VALIDINFOAREA_ATTRIBUTE, getValidInfoArea());
		
		Object items = getItems();
		if (items != null) {
			if (items != EMPTY) {
				Object itemsObject = evaluate("items", items);
				if (itemsObject != null) {
					final String selectName = getName();
					String valueProperty = (getItemValue() != null ?
							ObjectUtils.getDisplayString(evaluate("itemValue", getItemValue())) : null);
					String labelProperty = (getItemLabel() != null ?
							ObjectUtils.getDisplayString(evaluate("itemLabel", getItemLabel())) : null);
					OptionWriter optionWriter =
							new OptionWriter(itemsObject, getBindStatus(), valueProperty, labelProperty, isHtmlEscape(), getEmptyText()) {
								@Override
								protected String processOptionValue(String resolvedValue) {
									return processFieldValue(selectName, resolvedValue, "option");
								}
							};
					optionWriter.writeOptions(tagWriter);
				}
			}
			tagWriter.endTag(true);
			writeHiddenTagIfNecessary(tagWriter);
			return SKIP_BODY;
		}
		else {
			tagWriter.forceBlock();
			this.tagWriter = tagWriter;
			this.pageContext.setAttribute(LIST_VALUE_PAGE_ATTRIBUTE, getBindStatus());
			return EVAL_BODY_INCLUDE;
		}
	}

	private void writeHiddenTagIfNecessary(TagWriter tagWriter) throws JspException {
		if (isMultiple()) {
			tagWriter.startTag("input");
			tagWriter.writeAttribute("type", "hidden");
			String name = WebDataBinder.DEFAULT_FIELD_MARKER_PREFIX + getName();
			tagWriter.writeAttribute("name", name);
			tagWriter.writeAttribute("value", processFieldValue(name, "1", "hidden"));
			tagWriter.endTag();
		}
	}

	private boolean isMultiple() throws JspException {
		Object multiple = getMultiple();
		if (Boolean.TRUE.equals(multiple) || "multiple".equals(multiple)) {
			return true;
		}
		return forceMultiple();
	}

	private boolean forceMultiple() throws JspException {
		BindStatus bindStatus = getBindStatus();
		Class valueType = bindStatus.getValueType();
		if (valueType != null && typeRequiresMultiple(valueType)) {
			return true;
		}
		else if (bindStatus.getEditor() != null) {
			Object editorValue = bindStatus.getEditor().getValue();
			if (editorValue != null && typeRequiresMultiple(editorValue.getClass())) {
				return true;
			}
		}
		return false;
	}

	private static boolean typeRequiresMultiple(Class type) {
		return (type.isArray() || Collection.class.isAssignableFrom(type) || Map.class.isAssignableFrom(type));
	}

	@Override
	public int doEndTag() throws JspException {
		if (this.tagWriter != null) {
			this.tagWriter.endTag();
			writeHiddenTagIfNecessary(tagWriter);
		}
		return EVAL_PAGE;
	}

	@Override
	public void doFinally() {
		super.doFinally();
		this.tagWriter = null;
		this.pageContext.removeAttribute(LIST_VALUE_PAGE_ATTRIBUTE);
	}

}
