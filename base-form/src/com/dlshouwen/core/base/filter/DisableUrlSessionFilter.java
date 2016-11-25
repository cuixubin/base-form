package com.dlshouwen.core.base.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;

/**
 * 去掉URL中的jsession参数
 * @author 大连首闻科技有限公司
 * @version 2013-7-12 8:56:36
 */
public class DisableUrlSessionFilter implements Filter {

	/**
	 * 进行访问过滤，去除JSession信息
	 * @param request 请求对象
	 * @param response 响应对象
	 * @param chain 过滤对象
	 * @throws IOException, ServletException 抛出IO异常、Servlet异常
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (!(request instanceof HttpServletRequest)) {
			chain.doFilter(request, response);
			return;
		}
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		if (httpRequest.isRequestedSessionIdFromURL()) {
			HttpSession session = httpRequest.getSession();
			if (session != null)
				session.invalidate();
		}
		HttpServletResponseWrapper wrappedResponse = new HttpServletResponseWrapper(
				httpResponse) {
			@Override
			public String encodeRedirectUrl(String url) {
				return url;
			}

			public String encodeRedirectURL(String url) {
				return url;
			}

			public String encodeUrl(String url) {
				return url;
			}

			public String encodeURL(String url) {
				return url;
			}
		};
		chain.doFilter(request, wrappedResponse);
	}

	/**
	 * 过滤器初始化
	 * @param config 过滤器配置信息
	 * @throws ServletException 抛出Servlet异常
	 */
	public void init(FilterConfig config) throws ServletException {
	}

	/**
	 * 过滤器销毁
	 */
	public void destroy() {
	}

}
