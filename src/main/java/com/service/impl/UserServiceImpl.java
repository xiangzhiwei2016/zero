package com.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dao.OperateLogDao;
import com.entity.OperateLogTest;
import com.service.OperateService;
import com.service.UserService;

@Named("userService")
public class UserServiceImpl implements UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Inject
	OperateLogDao operateLogDao;
	
	@Inject
	OperateService operateService;



	public List<OperateLogTest> test(String ars) {
		logger.info("service²ã");
		return operateService.test(ars);
	}

}
