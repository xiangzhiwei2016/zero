package com.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SessionCookieUtils {
	private static final Logger logger = LoggerFactory.getLogger(SessionCookieUtils.class);
	
	 // session过期时间设置
    private static DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
    
    // cookie 中的token key
 	private static String token = "token";

 	// cookie 中的expireTime key
 	private static String expireTime = "expireTime";

 	
	public static Session getSessionFromCookie(HttpServletRequest request){
		String sessionId = null;
		Cookie[] cookies = request.getCookies();
		if(null != cookies){
			for(Cookie cookie : cookies){
				if(token.equals(cookie.getName())){
					sessionId = cookie.getValue();
				}
			}
		}
		if(null == sessionId){
			return null;
		}
		Subject subject = new Subject.Builder().sessionId(sessionId).buildSubject();
		if(null == subject){
			return null;
		}
		
		Session session = subject.getSession(false);
		if(null == session){
			return null;
		}
		// 如果当前session未登录
		if(!subject.isAuthenticated()){
			return subject.getSession(true);
		}
		return session;
	}
	
	 public static String getExpireTime(Session session) {
	        // 得到过期时间
	        long configTimeout = session.getTimeout();
	        long timeout = System.currentTimeMillis() + configTimeout;
	        // 过期时间格式转换为8+6格式
	        Date date = new Date(timeout);
	        return df.format(date);
	    }
	 
	 /**
		 * set cookies to response
		 * 
		 * @param request
		 * @param response
		 * @param session
		 */
		public static void setSessionCookies(HttpServletRequest request, HttpServletResponse response,
				Session session) {
			try {
				response.addCookie(getTokenCookieOfSession(session, request.getContextPath()));
				response.addCookie(getExpireTimeCookieOfSession(session, request.getContextPath()));
			} catch (Exception e) { // 避免shrio session id 过期的问题
			}
		}

		/**
		 * del cookies to response
		 * 
		 * @param request
		 * @param response
		 * @param session
		 */
		public static void delSessionCookies(HttpServletRequest request, HttpServletResponse response) {
			response.addCookie(getTokenCookieOfSession(null, request.getContextPath()));
			response.addCookie(getExpireTimeCookieOfSession(null, request.getContextPath()));
		}

		/**
		 * token cookies
		 * 
		 * @param session
		 * @param path
		 * @return
		 */
		public static Cookie getTokenCookieOfSession(Session session, String path) {

			Cookie cookieToken;
			if (session != null) {
				cookieToken = new Cookie(getCookieTokenKey(), session.getId().toString());
			} else {
				cookieToken = new Cookie(getCookieTokenKey(), null);
			}
			cookieToken.setHttpOnly(true);
			cookieToken.setMaxAge(-1);
			cookieToken.setPath(path);

			return cookieToken;
		}

		/**
		 * expire time cookies
		 * 
		 * @param session
		 * @param path
		 * @return
		 */
		public static Cookie getExpireTimeCookieOfSession(Session session, String path) {
			Cookie cookieExpireTime;

			if (session != null) {
				cookieExpireTime = new Cookie(getCookieExpireTimeKey(), getExpireTime(session));
			} else {
				cookieExpireTime = new Cookie(getCookieExpireTimeKey(), null);
			}
			cookieExpireTime.setHttpOnly(true);
			cookieExpireTime.setMaxAge(-1);
			cookieExpireTime.setPath(path);

			return cookieExpireTime;
		}
		
		/**
		 * 获取cookies中的token关键字
		 * 这里为了实现客户端，在访问不同的后端服务，可以通过不同的token设置，实现不同的后端，不同的session
		 * 
		 * @return
		 */
		public static String getCookieTokenKey() {
			return token;
		}

		/**
		 * 获取cookies中的expireTime关键字
		 * 
		 * @return
		 */
		public static String getCookieExpireTimeKey() {
			return expireTime;
		}

}
