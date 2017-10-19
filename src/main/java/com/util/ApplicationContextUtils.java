package com.util;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

@Named
@Singleton
public class ApplicationContextUtils {
	private static final Logger logger = LoggerFactory.getLogger(ApplicationContextUtils.class);
	@Inject
	ApplicationContext _context;
	
	public static ApplicationContext context;

	public ApplicationContext getContext() {
		return context;
	}

	public void setContext(ApplicationContext context) {
		this.context = context;
	}
	
	//初始化
	@PostConstruct
	public void init(){
		logger.info("初始化applicationContext......");
		context = _context;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName){
		Object obj = context.getBean(beanName);
		return (T) obj;
	}
	
}
