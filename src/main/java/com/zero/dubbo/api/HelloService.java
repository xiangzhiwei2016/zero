package com.zero.dubbo.api;

import com.zero.dubbo.annotation.Gateway;

@Gateway
public interface HelloService {
	String say(String hello);
}
