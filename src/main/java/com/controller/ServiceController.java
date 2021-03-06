package com.controller;

import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.entity.ResponseResult;
import com.service.FrameworkJsonService;

@Controller
public class ServiceController {
	@Inject
	private ApplicationContext context;
	@Inject
	@Lazy
	FrameworkJsonService frameworkJsonService;

	private static final Log logger = LogFactory.getLog(ServiceController.class);

	@RequestMapping(value = { "/service/{serviceName}/{funcName}" }, method = { RequestMethod.POST }, produces = {
			"text/plain;charset=UTF-8" })
	@ResponseBody
	public String doService(HttpServletRequest request, HttpServletResponse response, @PathVariable String serviceName,
			@PathVariable String funcName) {
		logger.info("serviceName:" + serviceName + ",funcName:" + funcName);
		ResponseResult result = new ResponseResult();

		String[] beanNames = context.getBeanDefinitionNames();

		logger.info("所有beanNames个数：" + beanNames.length);

		for (String bn : beanNames) {

			logger.info(bn);

		}
		boolean flag = this.context.containsBean(serviceName);
		if (!flag) {
			logger.error("请求无对应服务 " + serviceName + "." + funcName);
			return null;
		}
		Object bean = context.getBean(serviceName);
		Map<String, String> params = new HashMap();
		if ((request.getParameterNames() != null) && (request.getParameterNames().hasMoreElements())) {
			Enumeration<String> enumList = request.getParameterNames();
			String key = null;
			String value = null;
			while (enumList.hasMoreElements()) {
				key = (String) enumList.nextElement();
				value = request.getParameter(key);
				params.put(key, value);
			}
		}
		StringBuilder sb = new StringBuilder();
		for (String key : params.keySet()) {
			sb.append(key).append("=").append((String) params.get(key)).append("&");
		}
		logger.info(new StringBuilder().append("请求的参数requestParameters : ").append(sb.toString()).toString());
		// 通过反射调用方法
		Class<?> beanService = bean.getClass();
		Method method = null;
		Object data = null;
		try {
			method = beanService.getMethod("test", String.class);
			data = method.invoke(bean, "cc");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result.setData(data == null ? null : frameworkJsonService.toJson(data));

		response.setHeader("error_code", "200");
		return frameworkJsonService.toJson(result);
	}
}
