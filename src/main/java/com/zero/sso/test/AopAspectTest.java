package com.zero.sso.test;


import javax.inject.Named;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Aspect
@Named
public class AopAspectTest {
	private static final Logger logger = LoggerFactory.getLogger(AopAspectTest.class);
	@Pointcut("execution(public * com..service..*.*(..))")
	public void serviceExecution() {
	}

	@Before("serviceExecution()")
	public void setBefore() throws Throwable {
		logger.info("test,before,......................");
	}
}
