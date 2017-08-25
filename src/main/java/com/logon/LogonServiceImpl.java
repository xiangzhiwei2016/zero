package com.logon;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ConcurrentAccessException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.exception.ServiceException;
import com.session.CurrentSessionStoreFactory;
import com.shiro.RealmFactory;

@Named("logonService")
public class LogonServiceImpl implements LogonService{
	private static final Log logger = LogFactory.getLog(LogonServiceImpl.class);
	
	public static final String SESSION_PERMISSION = "session_permission";

	@Inject
	RealmFactory realmFactory;
	
	@Override
	public Session logon(String userName, String password,String host)  throws ServiceException{
		// 拿到subject
		Subject subject = new Subject.Builder().authenticated(false).buildSubject();
		// 若果session存在，登出
		if(null != subject.getSession(false)){
			subject.logout();
		}
		UsernamePasswordToken token = new UsernamePasswordToken(userName,password,host);
		// 认证
		try {
			subject.login(token);
		} catch (IncorrectCredentialsException | UnknownAccountException e) {
			logger.info("用户名或密码错误");
			throw new ServiceException(1, "用户名或密码错误", e);
		} catch (LockedAccountException e) {
			logger.info("账户锁定", e);
			throw new ServiceException(2, "账户锁定", e);
		} catch (ConcurrentAccessException e) {
			logger.info("用户已在别处登陆", e);
			throw new ServiceException(3, "用户已在别处登陆", e);
		} catch (ExcessiveAttemptsException e) {
			logger.info("输入错误次数超过系统允许最大次数", e);
			throw new ServiceException(4, "输入错误次数超过系统允许最大次数", e);
		} catch (ExpiredCredentialsException e) {
			logger.info("密码已过期", e);
			throw new ServiceException(5, "密码已过期", e);
		}catch (AuthenticationException e) {
			logger.info("用户名或密码错误");
			throw new ServiceException(1, "用户名或密码错误", e);
		} catch (Exception e) {
			logger.error("登陆发生未知异常 :", e);
			throw new ServiceException(100, "其他错误", e);
		}
		// 缓存权限
		setPermissionCache(subject);
		
		// 放置当前上线文的会话
		CurrentSessionStoreFactory.getCurrentSessionStore().setCurrentSession(subject.getSession());
		return subject.getSession();
	}

	/**
	 * 缓存权限
	 * @param subject
	 */
	public void setPermissionCache(Subject subject){
		Session session = subject.getSession();
		session.removeAttribute(SESSION_PERMISSION);
		Set<String> permissions = new HashSet<String>();
		AuthorizingRealm frameworkRealm = (AuthorizingRealm)realmFactory.getRealm();
		subject.hasRole("");
		Cache<Object, AuthorizationInfo> cache = frameworkRealm.getAuthorizationCache();
		for(AuthorizationInfo authorizationInfo : cache.values()){
			permissions.addAll(authorizationInfo.getStringPermissions());
		}
		// 把cache放到session中
		session.setAttribute(SESSION_PERMISSION, permissions);
		Object obj = session.getAttribute(SESSION_PERMISSION);
		logger.info("权限：" + obj.toString());
		// 清除缓存
		cache.clear();
	}
}
