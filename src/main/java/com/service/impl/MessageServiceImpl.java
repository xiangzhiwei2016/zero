package com.service.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.JdkVersion;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.configure.ConfigureProperties;
import com.dao.OperateLogDao;
import com.entity.Message;
import com.entity.OperateLogTest;
import com.repository.MessageRepository;
import com.service.MessageService;
import com.service.OperateService;
import com.sso.dao.FrameworkSSOService;
import com.zero.sso.service.SsoService;

@Named("messageService")
public class MessageServiceImpl implements MessageService {
	private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
	@Inject
	private ApplicationContext context;
	
	@Inject
	MessageRepository messageRepository;

	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public List<Message> findByCondition(BigDecimal hello,Message message) {
		Message mess = new Message();
		mess.setMsgID(Long.parseLong("111"));
		mess.setMsgTitle("test1111111");
		mess.setMessageCode("0");
		mess.setMsgBody("test");
		mess.setMsgType("0");
		mess.setFailureTime("0");
		mess.setMsgReceiver("test");
		mess.setMsgReceiverType("0");
		mess.setOperatorID("user");
//		mess = messageRepository.save(mess);
		logger.info("保存成功："+mess.toString());
		String str = null;
//		str.toString();
		return null;
		
		
	}
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		Method[] methods = MessageServiceImpl.class.getMethods();
		for(Method method : methods){
			String methodName = method.getName();
			Class<?>[] clss = method.getParameterTypes();
			Parameter[] ps = method.getParameters();
			for(Parameter p :ps){
				System.out.println("type:"+p.getType());
				System.out.println("ParameterizedType:"+p.getParameterizedType());
			}
			for(Class<?> cls : clss){
//				Object obj = cls.newInstance();
//				Field[] fs = obj.getClass().getDeclaredFields();
//				System.out.println("methodName:"+methodName+"，cls:"+cls.getName());
			}
			
		}
		
	}

	public List<Message> findByCondition(String hello) {
		logger.info("测试："+hello);
		List<Message> list = new ArrayList<Message>();
		Message message = new Message();
		message.setMessageCode("111");
		list.add(message);
		return list;
	}
	
}
