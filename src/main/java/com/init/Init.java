package com.init;

import javax.inject.Named;
import javax.inject.Singleton;

import org.springframework.beans.factory.InitializingBean;

@Named
@Singleton
public class Init implements InitializingBean{

	@Override
	public void afterPropertiesSet() throws Exception {
		// 初始化bean的时候
		System.out.println("--------------------------------");
		
	}

}
