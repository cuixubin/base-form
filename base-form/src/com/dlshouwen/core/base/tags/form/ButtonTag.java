package com.dlshouwen.core.base.tags.form;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import com.dlshouwen.core.base.config.CONFIG;
import com.dlshouwen.core.base.model.SessionUser;
import com.dlshouwen.core.base.tags.base.BaseHtmlTag;
import com.dlshouwen.core.base.utils.AttributeUtils;
import com.dlshouwen.core.base.utils.LimitUtils;
import com.dlshouwen.core.base.utils.TagUtils;

/**
 * 按钮控件
 * @author 大连首闻科技有限公司
 * @version 2015-12-2 14:12:51
 */
@SuppressWarnings({"serial"})
public class ButtonTag extends BaseHtmlTag {
	
	/** 按钮类型（button - reset） */
	private String type = "button";

	/** 按钮图标 */
	private String icon;
	
	/** 用于控制按钮权限 */
	private String limit;
	
	/** 用于显示按钮名称 */
	private String value;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public int doStartTag() throws JspException{
		StringBuffer handlers = new StringBuffer();
		handlers.append("<button ").append("type=\"").append(this.getType()).append("\" ");
//		处理按钮的样式
		StringBuffer styleClass = new StringBuffer();
		styleClass.append(this.getCssClass());
//		获取参数：无权限操作的按钮依然显示
		String show_button_when_no_limit = AttributeUtils.getAttributeContent(this.pageContext.getServletContext(), "show_button_when_no_limit");
//		如果没有权限，则给与无权限的css
		if(!this.isHaveLimit()){
			styleClass.append(" btn-disabled");
		}
//		如果无权限不显示则给与隐藏css
		if(!this.isHaveLimit()&&"0".equals(show_button_when_no_limit)){
			styleClass.append(" hide");
		}
		prepareAttribute(handlers, "class", styleClass);
		prepareBaseAttributes(handlers);
//		如果没有权限则处理禁用，并给与提示
		if(!this.isHaveLimit()){
			prepareAttribute(handlers, "disabled", "disabled");
			prepareAttribute(handlers, "title", "您无权执行该操作");
		}
		handlers.append(">");
		if(this.getIcon()!=null&&!"".equals(this.getIcon().trim()))
			handlers.append("<i class=\"fa fa-").append(this.getIcon()).append("\"></i>");
		if(this.getValue()!=null)
			handlers.append(this.getValue());
		handlers.append("</button>");
		TagUtils.getInstance().write(this.pageContext, handlers.toString());
		return EVAL_PAGE;
	}
	
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}
	
	/**
	 * 判断是否含有权限
	 * @return 是否含有权限
	 */
	private boolean isHaveLimit() {
		boolean isHaveLimit = true;
//		判断权限是否存在
		if(this.getLimit()!=null&&this.getLimit().trim().length()>0){
			HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
//			获取权限列表
			SessionUser sessionUser = (SessionUser)request.getSession().getAttribute(CONFIG.SESSION_USER);
			Map<String, String> limitInfo = sessionUser.getLimitInfo();
//			判断权限是否存在
			try {
				isHaveLimit = LimitUtils.checkLimit(this.getLimit(), limitInfo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			isHaveLimit = true;
		}
		return isHaveLimit;
	}
	
}
