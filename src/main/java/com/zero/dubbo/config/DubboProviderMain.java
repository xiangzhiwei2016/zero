package com.zero.dubbo.config;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;

import rmi.Client;

public class DubboProviderMain {
	
	@Test
	public void test() throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		String[] configLocations = StringUtils.tokenizeToStringArray("dubbo/zero/root-context-provider.xml", ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(configLocations);
		context.start();
		
	    Object lock = new Object();
        synchronized (lock) {
        	try {
				lock.wait();
			} catch (InterruptedException e) {				
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		new DubboProviderMain().test();
	}
}
