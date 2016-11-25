package com.dlshouwen.core.base.utils;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

/**
 * 标签工具类
 * @author 大连首闻科技有限公司
 * @version 2015-12-2 14:02:52
 */
public class TagUtils {

	/** 单例标签工具对象 */
	private static TagUtils tagUtils = null;
	
	/**
	 * 私有化构造方法
	 */
	private TagUtils(){
	}
	
	/**
	 * 获取标签工具类实例对象
	 * @return 标签工具类实例对象
	 */
	public static TagUtils getInstance(){
		tagUtils = new TagUtils();
		return tagUtils;
	}
	
	/**
	 * 写入内容
	 * <p>将content信息写入到页面内容
	 * @param pageContext page上下文
	 * @param content 需要写入的内容
	 */
	public void write(PageContext pageContext, String content){
		JspWriter out = pageContext.getOut();
		try {
			out.print(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
