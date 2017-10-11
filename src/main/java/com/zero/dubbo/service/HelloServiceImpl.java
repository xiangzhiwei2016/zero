package com.zero.dubbo.service;

import javax.inject.Named;

import com.zero.dubbo.api.HelloService;


@Named("helloService")
public class HelloServiceImpl implements HelloService{

	@Override
	public String say(String hello) {
		System.out.println("hello,"+hello);
		return hello;
	}
}
