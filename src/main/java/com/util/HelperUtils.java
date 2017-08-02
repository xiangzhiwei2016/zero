package com.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelperUtils {
	private static final Logger logger = LoggerFactory.getLogger(HelperUtils.class);
	
	public static List<Method> getAllInterfaceMethods(Class<?> clazz) {
		List<Method> list = new ArrayList<Method>();;
		Class<?>[] interfaces = null;
		if (clazz.isInterface()) {
			interfaces = new Class[] { clazz };
		}else{
			interfaces = clazz.getInterfaces();
		}
		for(Class<?> inter : interfaces ){
			Method[] methods = inter.getMethods();
			for(Method method : methods){
				list.add(method);
			}
		}
		return list;
	}
}
