package com.hteos.framework.context.http;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.NamedThreadLocal;

/**
 * 存放DispatcherSerlvlet执行的上下文
 * 
 * @see org.springframework.web.context.request.RequestContextHolder
 * **/
public class HttpRequestContextHolder {

	protected static final Log logger = LogFactory
			.getLog(HttpRequestContextHolder.class);

	private static final ThreadLocal<HttpServletResponse> responseHolder = new NamedThreadLocal<HttpServletResponse>(
			"Response Holder");
	
	private static final ThreadLocal<HttpServletRequest> requestHolder = new NamedThreadLocal<HttpServletRequest>(
			"Request Holder");

	public static void init(HttpServletRequest request,HttpServletResponse response) {
		requestHolder.set(request);
		if (response != null) {
			responseHolder.set(response);
		}
	}

	public static void reset() {
		responseHolder.remove();
	}

	public static HttpServletRequest getRequest() {
		return requestHolder.get();
	}

	public static HttpServletResponse getResponse() {
		return responseHolder.get();
	}

	public static HttpSession getSession() {
		return getRequest().getSession();
	}

	public static ServletContext getServletContext() {
		return getSession().getServletContext();
	}
}
