package com.hteos.framework.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.hteos.framework.bean.Position;
import com.hteos.framework.context.http.HttpRequestContextHolder;

public class IPUtils {

	private static final String IP_QUERY_URL = "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&&ip=";

	public static String getClientIp(){
		HttpServletRequest request = HttpRequestContextHolder.getRequest();
		String ip = request.getHeader("X-Real-IP");
		if(!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)){
			return ip;
		}else{
			return request.getRemoteAddr();
		}
	}
	
	public static Position getPosition(String ip) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Position position = objectMapper.readValue(new URL(IP_QUERY_URL + ip),Position.class);
			return position;
		} catch (Exception e1) {
		    e1.printStackTrace();
			return null;
		}
	}
	
	public static String getLongCity(String ip){
		try {
			Position position = getPosition(ip);
			return position.getCountry() + position.getProvince() + position.getCity();
		} catch (Exception e) {
			return "";
		}
	}

	public static String getCity(String ip) {
		try {
			return getPosition(ip).getCity();
		} catch (Exception e) {
			return "";
		}
	}
	
	public static void main(String[] args) {
		System.out.println(IPUtils.getPosition("219.137.52.167"));
	}
}
