package com.dlshouwen.core.base.extra.spring.converter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * 通用多文件上传解析器
 * @author 大连首闻科技有限公司
 * @version 2013-12-9 9:13:06
 */
public class MultipartResolver extends CommonsMultipartResolver {
	
	/**
	 * 判断是否为多文件上传
	 * @param request 请求对象
	 * @return 是否多文件
	 */
	@Override
	public boolean isMultipart(HttpServletRequest request) {
		return super.isMultipart(request);
	}

}
