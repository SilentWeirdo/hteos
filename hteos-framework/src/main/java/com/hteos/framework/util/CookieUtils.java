package com.hteos.framework.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.hteos.framework.context.http.HttpRequestContextHolder;

public class CookieUtils {

	public static String get(String name) {
		HttpServletRequest request = HttpRequestContextHolder.getRequest();
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (name.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
	public static void add(String name,String value,int days){
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(days * 24 * 60 * 60);
		HttpRequestContextHolder.getResponse().addCookie(cookie);
	}
	
	public static void remove(String name){
		
	}
}
