package com.service;

import java.math.BigDecimal;
import java.util.List;

import com.entity.Message;

public interface MessageService {
//	public List<Message> findByCondition(String hello);

	public List<Message> findByCondition(BigDecimal hello,Message message);
	
}
