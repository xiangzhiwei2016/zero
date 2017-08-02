package com.util;

import java.util.HashMap;
import java.util.Map;

public class StaticCache {
	private static StaticCache instance = new StaticCache();
	private Map<String, Object> cacheMap = new HashMap<String, Object>();

	public static StaticCache getInstance() {
		return instance;
	}

	public void put(String key, Object value) {
		this.cacheMap.put(key, value);
	}

	public Object get(String key) {
		return this.cacheMap.get(key);
	}
}