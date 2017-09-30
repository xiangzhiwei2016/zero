package com.dubbo.demo;


import javax.inject.Named;

@Named("demoService")
public class DemoServiceImpl implements DemoService{

	@Override
	public String sayHello(DubboRequest request) {
		return "hello"+request.getName();
	}
}
