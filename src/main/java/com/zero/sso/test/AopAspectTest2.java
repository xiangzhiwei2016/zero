package com.zero.sso.test;


import javax.inject.Named;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;


@Aspect
@Named
@Order(11)
public class AopAspectTest2 {
	private static final Logger logger = LoggerFactory.getLogger(AopAspectTest2.class);
	@Pointcut("execution(public * com..service..*.*(..))")
	public void serviceExecution() {
	}

	@Before("serviceExecution()")
	public void setBefore() throws Throwable {
		logger.info("AopAspectTest2,before,......................");
	}
}
