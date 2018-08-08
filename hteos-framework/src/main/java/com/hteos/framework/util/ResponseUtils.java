package com.hteos.framework.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseUtils {

	public static final String RESPONSE_SUCCESS_KEY = "success";

	public static final String RESPONSE_FAILURE_KEY = "failure";

	public static final String RESPONSE_TEXT_KEY = "text";

	public static final String RESPONSE_EXTRA_KEY = "extra";

	public static final String PAGINATION_ROOT_PROPERTY_KEY = "root";

	public static final String PAGINATION_TOTAL_PROPERTY_KEY = "total";

	public static <T> Map<String, Object> sendList(List<T> T) {
		Map<String, Object> map = getInstanceMap();
		map.put(PAGINATION_ROOT_PROPERTY_KEY, T);
		return map;
	}

	public static <T, V, K> Map<String, Object> sendList(Map<K,V> T) {
		Map<String, Object> map = getInstanceMap();
		map.put(PAGINATION_ROOT_PROPERTY_KEY, T);
		map.put(RESPONSE_SUCCESS_KEY, true);
		return map;
	}

	public static Map<String, Object> sendSuccess(String key, Object value) {
		Map<String, Object> map = getInstanceMap();
		map.put(RESPONSE_SUCCESS_KEY, true);
		map.put(key, value);
		return map;
	}

	public static Map<String, Object> sendFailure(String key, Object value) {
		Map<String, Object> map = getInstanceMap();
		map.put(RESPONSE_FAILURE_KEY, true);
		map.put(key, value);
		return map;
	}

	private static Map<String, Object> getInstanceMap() {
		return new HashMap<String, Object>();
	}

}
