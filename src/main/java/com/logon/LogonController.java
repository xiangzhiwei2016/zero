package com.logon;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.entity.ResponseResult;
import com.exception.ServiceException;
import com.service.FrameworkJsonService;
import com.util.SessionCookieUtils;

import utils.PsUtil;

@Controller
public class LogonController {
	private static final Logger logger = LoggerFactory.getLogger(LogonController.class);
	
	private static final String FORMAT = "text/plain;charset=utf-8";
	
	@Inject
	FrameworkJsonService  frameworkJsonService;
	
	@Inject
	LogonService logonService;
	
	@RequestMapping(value = "/logon",method = RequestMethod.POST,produces = FORMAT)
	@ResponseBody
	public String logon(HttpServletRequest request, HttpServletResponse response) throws ServiceException{
		
		// 定义返回值
		ResponseResult result = new ResponseResult();
		
		// 获取参数
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");		
		String host = request.getRemoteHost();
		if(null == PsUtil.convertToNull(userName) || null == PsUtil.convertToNull(password)){
			logger.info("用户名或密码错误");
			result.setErrorCode(1);
			result.setErrorMsg("用户名或密码错误");
			response.setHeader("error_code","200");
			return frameworkJsonService.toJson(result);
		}
		// 调用登录方法
		Session session = logonService.logon(userName, password,host);
		if(null == session){
			logger.info("用户登录失败，请刷新重试");
			result.setErrorCode(1);
			result.setErrorMsg("用户登录失败，请重试");
			response.setHeader("error_code","200");
			return frameworkJsonService.toJson(result);
		}
		logger.info("用户登录成功，sessionId:"+session.getId());
		// 头返回信息
		response.setHeader("error_code","200");
		SessionCookieUtils.setSessionCookies(request, response, session);
		return frameworkJsonService.toJson(result);
	}
}
