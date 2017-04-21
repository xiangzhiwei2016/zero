package com.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.dao.OperateLogDao;
import com.entity.OperateLogTest;
import com.service.OperateService;

@Named("operateService")
public class OperateServiceImpl implements OperateService{
	@Inject
	OperateLogDao operateDao;
	public List<OperateLogTest> test(String ars) {
		return operateDao.test();
	}

}
