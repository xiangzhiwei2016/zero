package com.dubbo.demo.consumer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dubbo.demo.DemoService;
import com.dubbo.demo.DubboRequest;

public class ConsumerMain {
	 public static void main(String[] args) throws Exception {
	        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"dubbo/dubbo_consumer.xml"});
	        context.start();

	        DemoService demoService = (DemoService)context.getBean("demoService"); // 获取远程服务代理
	        DubboRequest  request =  new DubboRequest();
	        request.setName("world");
	        String hello = demoService.sayHello(request); // 执行远程方法

	        System.out.println( hello ); // 显示调用结果
	    }
}
