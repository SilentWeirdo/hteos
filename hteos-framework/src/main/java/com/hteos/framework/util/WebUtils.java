package com.hteos.framework.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hteos.framework.context.http.HttpRequestContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class WebUtils extends org.springframework.web.util.WebUtils {

    private static Log logger = LogFactory.getLog(WebUtils.class);

    /**
     * 判断是否是异步的请求、AJAX请求
     *
     * @param request
     * @return boolean
     */
    public static boolean isAsynRequest(HttpServletRequest request) {
        if (request == null) {
            request = HttpRequestContextHolder.getRequest();
        }
        return (request.getHeader("x-requested-with") != null && request
                .getHeader("x-requested-with").equalsIgnoreCase(
                        "XMLHttpRequest"));
    }

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    public static String getClientIp() {
        HttpServletRequest request = getRequest();
        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        } else {
            return request.getRemoteAddr();
        }
    }

}
