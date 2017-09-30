package com.dubbo.demo;

import proxy.JDKDynamicProxy;
import proxy.RealSubject;
import proxy.Subject;

public class DemoProxy {
	public Object callService(){
		DemoService real = new DemoServiceImpl();
		Object obj = new JDKDynamicProxy().bind(real);
		return obj;
	}
}	
