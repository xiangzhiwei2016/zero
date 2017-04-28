package com.configure;

import java.io.InputStreamReader;
import java.util.Properties;

import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


@Named
@Singleton
public class ConfigureProperties implements Configuration {

	private static final Log logger = LogFactory.getLog(ConfigureProperties.class);
	private Properties properties = new Properties();

	public ConfigureProperties() {
		try {
			this.properties.load(new InputStreamReader(
					ConfigureProperties.class.getClassLoader().getResourceAsStream("configure.properties"), "UTF-8"));
		} catch (Exception arg1) {
			logger.error("∂¡»°≈‰÷√Œƒº˛configure.properties ß∞‹£°");
		}

	}

	public String getProperty(String key) {
		return this.properties.getProperty(key);
	}

	public String getProperty(String key, String defaultValue) {
		String value = this.properties.getProperty(key);
		return value == null ? defaultValue : value;
	}

}
