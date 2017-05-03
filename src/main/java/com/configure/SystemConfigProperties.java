package com.configure;

import java.io.InputStreamReader;
import java.util.Properties;

import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Named
public class SystemConfigProperties {
	private static final Log logger = LogFactory.getLog(SystemConfigProperties.class);
	private static final Properties properties = new Properties();

	public static String getProperty(String key) {
		return properties.getProperty(key);
	}

	public static String getProperty(String key, String defaultValue) {
		String value = properties.getProperty(key);
		return value != null && !StringUtils.isBlank(value) ? value : defaultValue;
	}

	public static void setProperty(String key, String value) {
		properties.setProperty(key, value);
	}

	static {
		Properties arg = properties;
		synchronized (properties) {
			try {
				properties.load(new InputStreamReader(
						ConfigureProperties.class.getClassLoader().getResourceAsStream("frameworkConfig.properties"),
						"UTF-8"));
			} catch (Exception arg2) {
				logger.fatal("读取配置文件frameworkConfig.properties失败！");
				throw new RuntimeException("读取配置文件frameworkConfig.properties失败！");
			}

		}
	}
}
