package com.zero.dubbo.config;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 * dubbo工具类
 * 
 * @author marshal.liu
 * @date 2015年8月29日
 * @version 1.0
 */
@Named
@Singleton
public class DubboUtils {

	/**
	 * 获取服务名
	 * 
	 * @param beanClassName
	 * @return
	 */
	public static String getRefName(String beanClassName) {
		String simpleName = beanClassName;
		try {
			simpleName = Class.forName(beanClassName).getSimpleName();
			simpleName = simpleName.substring(0, 1).toLowerCase()
					+ simpleName.substring(1);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		return simpleName;
	}
}
