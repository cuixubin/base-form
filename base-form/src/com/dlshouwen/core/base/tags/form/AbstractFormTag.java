package com.dlshouwen.core.base.tags.form;

import java.beans.PropertyEditor;
import javax.servlet.jsp.JspException;

import org.springframework.web.servlet.tags.HtmlEscapingAwareTag;

@SuppressWarnings("serial")
public abstract class AbstractFormTag extends HtmlEscapingAwareTag {

	protected Object evaluate(String attributeName, Object value) throws JspException {
		return value;
	}

	protected final void writeOptionalAttribute(TagWriter tagWriter, String attributeName, String value)
			throws JspException {

		if (value != null) {
			tagWriter.writeOptionalAttributeValue(attributeName, getDisplayString(evaluate(attributeName, value)));
		}
	}

	protected TagWriter createTagWriter() {
		return new TagWriter(this.pageContext);
	}

	@Override
	protected final int doStartTagInternal() throws Exception {
		return writeTagContent(createTagWriter());
	}

	protected String getDisplayString(Object value) {
		return ValueFormatter.getDisplayString(value, isHtmlEscape());
	}

	protected String getDisplayString(Object value, PropertyEditor propertyEditor) {
		return ValueFormatter.getDisplayString(value, propertyEditor, isHtmlEscape());
	}

	@Override
	protected boolean isDefaultHtmlEscape() {
		Boolean defaultHtmlEscape = getRequestContext().getDefaultHtmlEscape();
		return (defaultHtmlEscape == null || defaultHtmlEscape.booleanValue());
	}

	protected abstract int writeTagContent(TagWriter tagWriter) throws JspException;

}
