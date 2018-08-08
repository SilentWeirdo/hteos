package com.hteos.framework.interceptor.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hteos.framework.context.http.HttpRequestContextHolder;
import com.hteos.framework.interceptor.AbstractHandlerPreparInterceptor;

public class HttpRequestContextInterceptor extends AbstractHandlerPreparInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HttpRequestContextHolder.init(request, response);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        HttpRequestContextHolder.reset();
    }
}
