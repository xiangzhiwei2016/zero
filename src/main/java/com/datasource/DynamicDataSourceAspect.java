package com.datasource;

import javax.inject.Named;
import javax.inject.Singleton;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;

import com.util.MutiDataSourceHelper;

@Aspect
@Named
@Singleton
public class DynamicDataSourceAspect {
	private boolean isMultiDatasouce = true;

	DynamicDataSourceAspect() {
		if (MutiDataSourceHelper.getPackageDataSourceMap().keySet().size() == 0) {
			this.isMultiDatasouce = false;
		}

	}

	@Pointcut("execution(public * com..service..*.*(..))")
	public void serviceExecution() {
	}

	@Around("serviceExecution()")
	@Order(100)
	public Object setDynamicDataSource(ProceedingJoinPoint jp) throws Throwable {
		if (!this.isMultiDatasouce) {
			return jp.proceed();
		} else {
			String className = jp.getSignature().getDeclaringType().getName();
			MethodSignature signature = (MethodSignature) jp.getSignature();
			String methodName = signature.getMethod().getName();
			StringBuilder callPath = new StringBuilder();
			callPath.append(className).append(".").append(methodName);
			String dbType = DBContextHolder.getDBType();
			DBContextHolder.setDBType(MutiDataSourceHelper.getPackageDataSource(callPath.toString()));
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