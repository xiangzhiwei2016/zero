package com.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.configure.ConfigureProperties;
import com.dao.OperateLogDao;
import com.entity.OperateLogTest;
import com.service.OperateService;
import com.service.UserService;
import com.sso.dao.FrameworkSSOService;
import com.xzw.zero.mapper.UserMapper;

@Named("userService")
//@Component // 可有可无
public class UserServiceImpl implements UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	@Inject
	private ApplicationContext context;
	
	@Inject
	OperateLogDao operateLogDao;
	
	@Inject
	OperateService operateService;
	
	@Inject
	ConfigureProperties configureProperties;
	
	@Inject
	FrameworkSSOService frameworkSSOService;
	
//	@Inject
//	UserMapper userMapper;


	@Scheduled(cron="0/5 * *  * * ? ")   //每5秒执行一次  
	public List<OperateLogTest> test() {
		logger.info("service层");
		String ars = "s";
		String username = configureProperties.getProperty("username");
		
		String password = configureProperties.getProperty("password","111");
		logger.info("username:"+username+",password:"+password);
		String[] beanNames = context.getBeanDefinitionNames();

		logger.info("所有beanNames个数：" + beanNames.length);

		for (String bn : beanNames) {

			logger.info(bn);

		}

//		operateService.test(ars);
//		frameworkSSOService.testsso();
//		List<OperateLogTest> olist = userMapper.findByMyBatis();
//		for(OperateLogTest o : olist){
//			logger.info(o.toString());
//		}
		return null;
	}

	public List<OperateLogTest> test2(String ars) {
		// TODO Auto-generated method stub
		return null;
	}
}
