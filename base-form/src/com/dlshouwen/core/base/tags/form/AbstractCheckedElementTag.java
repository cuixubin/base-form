package com.dlshouwen.core.base.tags.form;

import javax.servlet.jsp.JspException;

@SuppressWarnings("serial")
public abstract class AbstractCheckedElementTag extends AbstractHtmlInputElementTag {

	protected void renderFromValue(Object value, TagWriter tagWriter) throws JspException {
		renderFromValue(value, value, tagWriter);
	}

	protected void renderFromValue(Object item, Object value, TagWriter tagWriter) throws JspException {
		String displayValue = convertToDisplayString(value);
		tagWriter.writeAttribute("value", processFieldValue(getName(), displayValue, getInputType()));
		if (isOptionSelected(value) || (value != item && isOptionSelected(item))) {
			tagWriter.writeAttribute("checked", "checked");
		}
	}

	private boolean isOptionSelected(Object value) throws JspException {
		return SelectedValueComparator.isSelected(getBindStatus(), value);
	}

	protected void renderFromBoolean(Boolean boundValue, TagWriter tagWriter) throws JspException {
		tagWriter.writeAttribute("value", processFieldValue(getName(), "true", getInputType()));
		if (boundValue) {
			tagWriter.writeAttribute("checked", "checked");
		}
	}

	@Override
	protected String autogenerateId() throws JspException {
		return TagIdGenerator.nextId(super.autogenerateId(), this.pageContext);
	}

	@Override
	protected abstract int writeTagContent(TagWriter tagWriter) throws JspException;

	@Override
	protected boolean isValidDynamicAttribute(String localName, Object value) {
		return !"type".equals(localName);
	}

	protected abstract String getInputType();

}
