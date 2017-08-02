package com.zero.sso.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.entity.OperateLogTest;
import com.repository.OperateLogTestRepository;
import com.service.OperateService;
import com.zero.sso.service.SsoService;

@Named("ssoService")
public class SsoServiceImpl implements SsoService{
	Logger logger = LoggerFactory.getLogger(SsoServiceImpl.class);
	
	@Inject
	private ApplicationContext context;
	
	@Inject
	OperateService operateService;

	@Inject
	private OperateLogTestRepository operateLogTestRepository;
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void test(String hello) {
		logger.info("数据源切换," + hello);
		List<OperateLogTest>  list = operateService.test(null);
		
		list.toString();
		
		OperateLogTest test = new OperateLogTest();
		test.setJobName("20170716_test3");
//		operateService.save(test);
//		String str = null;
//		str.toString();
	}

//	@Scheduled(cron="0/5 * *  * * ? ")   //ÿ5��ִ��һ��  
	public void testDataSource(){
		logger.info("测试,...................");
		String[] beanNames = context.getBeanDefinitionNames();

		logger.info("扫描到的beanNames" + beanNames.length);

		for (String bn : beanNames) {

			logger.info("beanName:"+bn);

		}
		test("1212");
	}
	
	public void testSystem(){
		
	}
}
