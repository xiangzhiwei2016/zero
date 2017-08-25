package com.interceptor;

import java.util.Enumeration;
import java.util.StringJoiner;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.session.CurrentSessionStoreFactory;
import com.util.SessionCookieUtils;

@Named
@Singleton
public class SessionInterceptor implements HandlerInterceptor{

	private static final Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception arg3)
			throws Exception {
		logger.info("request url :" + request.getServletPath() + " "
				+ request.getMethod() + " returned");	
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView arg3)
			throws Exception {
		// 清除session
		CurrentSessionStoreFactory.getCurrentSessionStore().removeCurrentSession();
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		request.setCharacterEncoding("utf-8");
		logger.info(String.format(
					"request url :%s device_uuid:%s device_os:%s app_version%s",
					request.getServletPath() + " " + request.getMethod(),
					request.getHeader("device_uuid"),
					request.getHeader("device_os"),
					request.getHeader("app_version")));
		
		StringJoiner sj = new StringJoiner(",");
		sj.add(request.getRemoteHost());
		sj.add(request.getRemoteAddr());
		sj.add(String.valueOf(request.getRemotePort()));
		logger.info("remote:" + sj.toString());
		
		Enumeration<String> headers = request.getHeaderNames();
		while(headers.hasMoreElements()){
			String header = headers.nextElement();
			logger.info("http request header: "+header+":"+request.getHeader(header));
		}
		// common header
		response.setCharacterEncoding("utf-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-control", "No-cache");
		response.setDateHeader("Expires", 0);
		
		// session
		Session session = SessionCookieUtils.getSessionFromCookie(request);
		if(null != session){
			CurrentSessionStoreFactory.getCurrentSessionStore().setCurrentSession(session);
		}
		return true;
	}

}
