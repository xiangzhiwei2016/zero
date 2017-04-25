package com.dao.impl;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.Modifying;

import com.dao.OperateLogDao;
import com.entity.OperateLogTest;
import com.mchange.v1.util.IteratorUtils;
import com.repository.OperateLogTestRepository;

@Named("operateLogDao")
public class OperateLogDaoImpl implements OperateLogDao {
	private Logger logger = LoggerFactory.getLogger(OperateLogDaoImpl.class);

	@Inject
	OperateLogTestRepository operateLogTestRepository;

	@Inject
	EntityManager entityManager;

	public List<OperateLogTest> test() {
		logger.info("OperateLogDaoImpl≤‚ ‘");

		logger.info("±£¥Ê");
//		OperateLogTest o = new OperateLogTest("1", "2", "1", "2", "1", "2", "1", "2", "1", "2");
//		// ±£¥Ê
//		operateLogTestRepository.save(o);
		Query query = entityManager.createQuery("select t from OperateLogTest t ", OperateLogTest.class);
		List result = query.getResultList();
		if (result != null) {
			Iterator iterator = result.iterator();
			while (iterator.hasNext()) {
				OperateLogTest test = (OperateLogTest) iterator.next();
				logger.info(test.toString());
			}
		}
		Iterator<OperateLogTest> list = operateLogTestRepository.findAll().iterator();
		List<OperateLogTest> ls = IteratorUtils.toArrayList(list, 0);
		return ls;
	}

}
