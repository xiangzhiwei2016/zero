package com.datasource;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import com.anotation.DataSource;
import com.util.MutiDataSourceHelper;

@Aspect
@Named
@Singleton
public class DynamicDataSourceAspect {
	private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);
	
	private boolean isMultiDatasouce = true;
	
	@Inject
	private MultiDataSources multiDataSources;
	
	DynamicDataSourceAspect() {
		if (MutiDataSourceHelper.getPackageDataSourceMap().keySet().size() == 0) {
			this.isMultiDatasouce = false;
		}

	}

	@Pointcut("execution(public * com..service..*.*(..))")
	public void serviceExecution() {
	}

	@Around("serviceExecution()")
	@Order(0)
	public Object setDynamicDataSource(ProceedingJoinPoint jp) throws Throwable {
		logger.info("数据源切换aop.................");
		if (!this.isMultiDatasouce) {
			return jp.proceed();
		} else {
			String className = jp.getSignature().getDeclaringType().getName();
			
			MethodSignature signature = (MethodSignature) jp.getSignature();
			String methodName = signature.getMethod().getName();
			StringBuilder callPath = new StringBuilder();
			callPath.append(className).append(".").append(methodName);
			String dbType = DBContextHolder.getDBType();
			logger.info("dbType:"+dbType);
//			DBContextHolder.setDBType(MutiDataSourceHelper.getPackageDataSource(callPath.toString()));
			Class<?> clazz = Class.forName(className);
			DataSource[] dsArr = (DataSource[])clazz.getAnnotationsByType(DataSource.class);
			if(null != dsArr && dsArr.length > 0){
				DataSource annotation = clazz.getAnnotation(DataSource.class);
				dbType = annotation.name();
				DBContextHolder.setDBType(dbType);
			}else{
				DBContextHolder.clearDBType();
			}
			String dbType2 = DBContextHolder.getDBType();
			logger.info("dbType2:"+dbType2);
			javax.sql.DataSource dataSource = (javax.sql.DataSource)multiDataSources.getDataSource(dbType);
			dataSource.getConnection().setAutoCommit(false);
			logger.info("dataSource.transaction"+dataSource.toString());
			Object obj = jp.proceed();
			if (dbType != null && !"".equals(dbType)) {
				DBContextHolder.setDBType(dbType);
			} else {
				DBContextHolder.clearDBType();
			}
			return obj;
		}
	}
}