package com.dlshouwen.core.base.tags.base;

import javax.servlet.jsp.tagext.TagSupport;

@SuppressWarnings("serial")
public abstract class BaseHtmlTag extends TagSupport {
	
	private String id;
	
	private String name;
	
	private String accesskey;
	
	private String size;
	
	private String maxlength;
	
	private String alt;
	
	private String readonly;
	
	private String disabled;

	private String cssClass;

	private String cssStyle;

	private String lang;

	private String title;

	private String dir;

	private String tabindex;

	private String onclick;

	private String ondblclick;

	private String onmousedown;

	private String onmouseup;

	private String onmouseover;

	private String onmousemove;

	private String onmouseout;

	private String onkeypress;

	private String onkeyup;

	private String onkeydown;
	
	private String onfocus;
	
	private String onblur;
	
	private String onchange;
	
	private String onselect;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccesskey() {
		return accesskey;
	}

	public void setAccesskey(String accesskey) {
		this.accesskey = accesskey;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getMaxlength() {
		return maxlength;
	}

	public void setMaxlength(String maxlength) {
		this.maxlength = maxlength;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	public String getReadonly() {
		return readonly;
	}

	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getCssStyle() {
		return cssStyle;
	}

	public void setCssStyle(String cssStyle) {
		this.cssStyle = cssStyle;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getTabindex() {
		return tabindex;
	}

	public void setTabindex(String tabindex) {
		this.tabindex = tabindex;
	}

	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public String getOndblclick() {
		return ondblclick;
	}

	public void setOndblclick(String ondblclick) {
		this.ondblclick = ondblclick;
	}

	public String getOnmousedown() {
		return onmousedown;
	}

	public void setOnmousedown(String onmousedown) {
		this.onmousedown = onmousedown;
	}

	public String getOnmouseup() {
		return onmouseup;
	}

	public void setOnmouseup(String onmouseup) {
		this.onmouseup = onmouseup;
	}

	public String getOnmouseover() {
		return onmouseover;
	}

	public void setOnmouseover(String onmouseover) {
		this.onmouseover = onmouseover;
	}

	public String getOnmousemove() {
		return onmousemove;
	}

	public void setOnmousemove(String onmousemove) {
		this.onmousemove = onmousemove;
	}

	public String getOnmouseout() {
		return onmouseout;
	}

	public void setOnmouseout(String onmouseout) {
		this.onmouseout = onmouseout;
	}

	public String getOnkeypress() {
		return onkeypress;
	}

	public void setOnkeypress(String onkeypress) {
		this.onkeypress = onkeypress;
	}

	public String getOnkeyup() {
		return onkeyup;
	}

	public void setOnkeyup(String onkeyup) {
		this.onkeyup = onkeyup;
	}

	public String getOnkeydown() {
		return onkeydown;
	}

	public void setOnkeydown(String onkeydown) {
		this.onkeydown = onkeydown;
	}

	public String getOnfocus() {
		return onfocus;
	}

	public void setOnfocus(String onfocus) {
		this.onfocus = onfocus;
	}

	public String getOnblur() {
		return onblur;
	}

	public void setOnblur(String onblur) {
		this.onblur = onblur;
	}

	public String getOnchange() {
		return onchange;
	}

	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}

	public String getOnselect() {
		return onselect;
	}

	public void setOnselect(String onselect) {
		this.onselect = onselect;
	}

	protected void prepareAttribute(StringBuffer handlers, String name, Object value) {
        if (value != null) {
        	handlers.append(" ").append(name).append("=\"").append(value).append("\"");
        }
    }
	
	protected void prepareBaseAttributes(StringBuffer handlers){
		prepareAttribute(handlers, "id", this.getId());
		prepareAttribute(handlers, "name", this.getName());
		prepareAttribute(handlers, "accesskey", this.getAccesskey());
		prepareAttribute(handlers, "size", this.getSize());
		prepareAttribute(handlers, "maxlength", this.getMaxlength());
		prepareAttribute(handlers, "alt", this.getAlt());
		prepareAttribute(handlers, "readonly", this.getReadonly());
		prepareAttribute(handlers, "disabled", this.getDisabled());
		prepareAttribute(handlers, "class", this.getCssClass());
		prepareAttribute(handlers, "style", this.getCssStyle());
		prepareAttribute(handlers, "lang", this.getLang());
		prepareAttribute(handlers, "title", this.getTitle());
		prepareAttribute(handlers, "dir", this.getDir());
		prepareAttribute(handlers, "tabindex", this.getTabindex());
		prepareAttribute(handlers, "onclick", this.getOnclick());
		prepareAttribute(handlers, "ondblclick", this.getOndblclick());
		prepareAttribute(handlers, "onmousedown", this.getOnmousedown());
		prepareAttribute(handlers, "onmouseup", this.getOnmouseup());
		prepareAttribute(handlers, "onmouseover", this.getOnmouseover());
		prepareAttribute(handlers, "onmousemove", this.getOnmousemove());
		prepareAttribute(handlers, "onmouseout", this.getOnmouseout());
		prepareAttribute(handlers, "onkeypress", this.getOnkeypress());
		prepareAttribute(handlers, "onkeyup", this.getOnkeyup());
		prepareAttribute(handlers, "onkeydown", this.getOnkeydown());
		prepareAttribute(handlers, "onfocus", this.getOnfocus());
		prepareAttribute(handlers, "onblur", this.getOnblur());
		prepareAttribute(handlers, "onchange", this.getOnchange());
		prepareAttribute(handlers, "onselect", this.getOnselect());
	}
	
	public void doFinally() {
	}

}
