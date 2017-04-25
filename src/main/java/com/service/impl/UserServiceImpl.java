package com.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dao.OperateLogDao;
import com.entity.OperateLogTest;
import com.service.OperateService;
import com.service.UserService;

@Named("userService")
//@Component // ���п���
public class UserServiceImpl implements UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Inject
	OperateLogDao operateLogDao;
	
	@Inject
	OperateService operateService;

	@Scheduled(cron="0/5 * *  * * ? ")   //ÿ5��ִ��һ��  
	public List<OperateLogTest> test() {
		logger.info("service��");
		String ars = "s";
		return operateService.test(ars);
	}

	public List<OperateLogTest> test2(String ars) {
		// TODO Auto-generated method stub
		return null;
	}
}
