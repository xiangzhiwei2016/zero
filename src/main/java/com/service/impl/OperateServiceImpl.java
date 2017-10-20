package com.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Lazy;

import com.dao.OperateLogDao;
import com.entity.OperateLogTest;
import com.pageable.FrameworkPageable;
import com.service.OperateService;
import com.xzw.zero.mapper.UserMapper;

@Named("operateService")
public class OperateServiceImpl implements OperateService{
	@Inject
	OperateLogDao operateDao;
			
	@Inject
	UserMapper userMapper;
	
	@Inject
	@Lazy
	FrameworkPageable frameworkPageable;
	
	public List<OperateLogTest> test(String ars) {
		OperateLogTest tests = new OperateLogTest();
		tests.setJobName("xzw");
		save(tests);
		System.out.println(ars+"...............");
		List<OperateLogTest> list = userMapper.findByMyBatis("1");
		for(OperateLogTest test:list){
			System.out.println(test.toString());
		}
		// 删除
		operateDao.removeAll();
		
		return operateDao.test();
	}
	public void save(OperateLogTest test) {
		operateDao.save(test);
	}

}
