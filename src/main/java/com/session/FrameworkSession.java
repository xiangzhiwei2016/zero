package com.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

public interface FrameworkSession {
	public static FrameworkSession getSessionFromSessionId(String sessionId){
		Subject subject = new Subject.Builder().sessionId(sessionId).buildSubject();
		if(null == subject){
			return null;
		}
		Session session = subject.getSession(false);
		if(null == session){
			return null;
		}
		// 如果当前session还未登录
		if(!subject.isAuthenticated()){
//			return new FrameworkSessionImpl(session);
		}
		return null;
	}
}
