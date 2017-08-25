package com.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.inject.Named;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by fan on 15/6/29.
 */
@Named("defaultRealm")
public class DefaultRealm extends AuthorizingRealm {
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principalCollection) {
		// 所有的权限
		Set<String> permission = new TreeSet<String>();
		// permission.add("*:*:");

		Set<String> role = new TreeSet<String>();
		role.add("Guest");

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setRoles(role);
		info.setStringPermissions(permission);

		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		return new SimpleAuthenticationInfo("Guest", "Guest", "DefaultRealm");
	}
}
