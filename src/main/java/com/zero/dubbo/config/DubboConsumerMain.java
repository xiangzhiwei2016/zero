package com.zero.dubbo.config;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;

import com.zero.dubbo.api.HelloService;


public class DubboConsumerMain {
	
	@Test
	public void test() throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		String[] configLocations = StringUtils.tokenizeToStringArray("dubbo/zero/root-context-consumer.xml", ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(configLocations);
//		context.start();
	
		Object obj = context.getBean(HelloService.class.getName());
		System.out.println(obj.getClass().getSimpleName());
		System.out.println(obj.getClass().getName());
		HelloService helloService = (HelloService)obj;
		String result = helloService.say("word");
		System.out.println(result);
	}
	public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		new DubboConsumerMain().test();
	}
}
