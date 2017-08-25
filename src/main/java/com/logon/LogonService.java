package com.logon;

import org.apache.shiro.session.Session;

import com.exception.ServiceException;


public interface LogonService {
	public Session logon(String userName,String password,String host) throws ServiceException;
}
