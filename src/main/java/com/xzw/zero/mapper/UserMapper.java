package com.xzw.zero.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.entity.OperateLogTest;

public interface UserMapper {
	public List<OperateLogTest> findByMyBatis(@Param("id") String id);
}
