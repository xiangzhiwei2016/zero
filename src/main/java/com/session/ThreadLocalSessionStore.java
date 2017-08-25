package com.session;

import org.apache.shiro.session.Session;

/**
 * 当前会话保存到threadlocal 
 */
public class ThreadLocalSessionStore implements CurrentSessionStore {

	/**
	 * thread local
	 */
	private static ThreadLocal<Session> threadLocalSession = new ThreadLocal<>();

	/**
	 * use threadlocal save session id by current thread
	 *
	 * @param session
	 */
	public void removeCurrentSession() {
		threadLocalSession.remove();
	}

	/**
	 * @return
	 */
	public Session getCurrentSession() {
		return threadLocalSession.get();
	}

	/**
	 * use threadlocal save session id by current thread
	 *
	 * @param sessionId
	 */
	public void setCurrentSession(Session session) {

		if (session == null) {
			return;
		}
		threadLocalSession.set(session);
	}
}
