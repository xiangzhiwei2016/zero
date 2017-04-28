package com.configure;

public interface Configuration {
	String getProperty(String propertyName);

	String getProperty(String propertyName, String defaultValue);
}
