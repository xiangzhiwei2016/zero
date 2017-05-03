package com.sso.dao;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dao.impl.OperateLogDaoImpl;
import com.entity.OperateLogTest;

@Named
public class FrameworkSSOService {
	private Logger logger = LoggerFactory.getLogger(OperateLogDaoImpl.class);

	@Inject
	EntityManager entityManager;

	public void testsso(){
		Query query = entityManager.createQuery("select t from OperateLogTest t ", OperateLogTest.class);
		List result = query.getResultList();
		if (result != null) {
			Iterator iterator = result.iterator();
			while (iterator.hasNext()) {
				OperateLogTest test = (OperateLogTest) iterator.next();
				logger.info(test.toString());
			}
		}
	}
	
}
