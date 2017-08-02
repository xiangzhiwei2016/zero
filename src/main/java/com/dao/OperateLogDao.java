package com.dao;

import java.util.List;

import com.entity.OperateLogTest;

public interface OperateLogDao {
	List<OperateLogTest> test();
	
	void save(OperateLogTest test);
}
