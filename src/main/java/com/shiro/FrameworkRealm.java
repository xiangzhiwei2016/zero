package com.shiro;

import java.util.Set;
import java.util.TreeSet;

import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named("frameworkRealm")
@Singleton
public class FrameworkRealm extends AuthorizingRealm{

	private static final Logger logger = LoggerFactory.getLogger(FrameworkRealm.class);
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		logger.info("1111111111111111111111111");
		// 登录id
		String logonId = (String)getAvailablePrincipal(arg0);
		// 角色
		Set<String> roles = new TreeSet<String>();
		roles.add("xzw");
		// 权限
		Set<String> permission = new TreeSet<String>();
		permission.add("service:*.*");
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setRoles(roles);
		info.setStringPermissions(permission);
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken paramAuthenticationToken)
			throws AuthenticationException {
		logger.info("22222222222222222222222222");
		UsernamePasswordToken token = (UsernamePasswordToken)paramAuthenticationToken;
		// 验证用户名密码
		String userName = token.getUsername();
		String password = String.valueOf(token.getPassword());
		// 判断
		if("xzw".equals(userName) && "12345678".equals(password)){
			// 验证通过
			logger.info("用户【"+userName+"】验证通过");
			return new SimpleAuthenticationInfo(token.getUsername(), token.getPassword(), "frameworkRealm");
		}else{
			// 不通过
			logger.info("用户【"+userName+"】验证不通过");
			throw new AuthenticationException("wrong password");
		}
	}

}
