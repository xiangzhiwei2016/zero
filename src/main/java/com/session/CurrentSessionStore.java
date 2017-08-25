package com.session;

import org.apache.shiro.session.Session;

/**
 * 保存当前执行环境中的session
 * @author xiangzhiwei
 *
 */
public interface CurrentSessionStore {
	void removeCurrentSession();
	Session getCurrentSession();
	void setCurrentSession(Session session);
}
