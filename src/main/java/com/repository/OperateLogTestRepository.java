package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.entity.OperateLogTest;


@Repository("operateLogTestRepository")
public interface OperateLogTestRepository extends CrudRepository<OperateLogTest, Long> {
	
	@Query(value = "select c from OperateLogTest c where (c.jobName = :jobName or :jobName is null) ")
	public List<OperateLogTest> findByCondition(@Param("jobName") String jobName);
}
