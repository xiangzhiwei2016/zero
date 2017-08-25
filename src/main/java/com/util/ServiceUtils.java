package com.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import com.google.gson.Gson;
import com.service.FrameworkJsonService;
import com.service.impl.MessageServiceImpl;
import com.session.CurrentSessionStoreFactory;



public class ServiceUtils {
	private static final Logger logger = LoggerFactory.getLogger(ServiceUtils.class);
	
	public static Object callService(Object service, String funcName, Map<String, String> params) {
		Object result = null;
		Method realMethod = null;
		Parameter[] parameters = null;
		try {
			realMethod = getInvokeMethod(service, funcName);
			if (null == realMethod) {
				logger.info("there is no method called" + service+"."+funcName);
				return null;
			}
			if(!hasPermission()){
				logger.info("无权限访问");
				return null;
			}
			parameters = realMethod.getParameters();
			if (isParameterizedType(service)) {
				Method objects = getTargetMethod(service, realMethod);
				parameters = objects.getParameters();
			}
			// 参数数组
			Object[] obj = new Object[parameters.length];
			String key = null;
			String value = null;
			for (int i = 0; i < parameters.length; i++) {
				key = parameters[i].getName();
				value = params.get(key);
				if (null != value && !"NULL".equals(value.toUpperCase())) {
					if (isBaseType(parameters[i].getType())) {
						obj[i] = BaseTypeUtils.string2BaseType(value, parameters[i].getType());
					} else if (String.class.equals(parameters[i].getType())) {
						obj[i] = value;
					} else {
						obj[i] = new Gson().fromJson(value, parameters[i].getParameterizedType());
					}
				}else if (!BigDecimal.class.equals(parameters[i].getType()) && !isBaseType(parameters[i].getType()) && !String.class.equals(parameters[i].getType())) {
					obj[i] = parameterMap2Object(parameters[i].getType(), params);
				} else {
					obj[i] = null;
				}
			}
			// 反射
			result = realMethod.invoke(service, obj);

		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static boolean  isBaseType(Class<?> cls){ 
		boolean flag = false;
		if(Byte.TYPE.equals(cls) || Byte.class.equals(cls) || Short.TYPE.equals(cls) || Short.class.equals(cls)
				|| Integer.TYPE.equals(cls) || Integer.class.equals(cls) || Long.TYPE.equals(cls)
				|| Long.class.equals(cls) || Float.TYPE.equals(cls) || Float.class.equals(cls)
				|| Double.TYPE.equals(cls) || Double.class.equals(cls) || Character.TYPE.equals(cls)
				|| Character.class.equals(cls) || Boolean.TYPE.equals(cls) || Boolean.class.equals(cls)){
			flag = true;
		}
		return flag;
	}
	
	public static Object[] parameterMap2ObjectArray(FrameworkJsonService frameworkJsonService,
			Map<String, String> params, Method method, Object service) throws Exception {
		Parameter[] parameters = method.getParameters();

		if (parameters != null && parameters.length > 0) {
			Object[] arg8 = new Object[parameters.length];
			String key = null;
			String value = null;

			for (int i = 0; i < parameters.length; ++i) {
				if (true) {
					key = parameters[i].getName();
					value = (String) params.get(key);
					if (null != value && !"NULL".equals(value.toUpperCase()) ) {
						if (BaseTypeUtils.isBaseType(parameters[i].getType())) {
							arg8[i] = BaseTypeUtils.string2BaseType(value, parameters[i].getType());
						} else if (String.class.equals(parameters[i].getType())) {
							arg8[i] = value;
						} else {
							arg8[i] = new Gson().fromJson(value, parameters[i].getParameterizedType());
						}
					} else if (!BaseTypeUtils.isBaseType(parameters[i].getType())
							&& !String.class.equals(parameters[i].getType())) {
						arg8[i] = parameterMap2Object(parameters[i].getType(), params);
					} else {
						arg8[i] = null;
					}
				}
			}

			return arg8;
		} else {
			return null;
		}
	}

	private static Object parameterMap2Object(Class<?> cls, Map<String, String> params) {
		Object result = null;
		boolean flag = false;
		try {
			result = cls.newInstance();
			Field[] fields = cls.getDeclaredFields();
			String property = null;
			String value = null;
			Object targetValue = null;
			if (null != fields) {
				for (Field field : fields) {
					property = field.getName();
					value = params.get(property);
					if (!"NULL".equals(value) && value != null && !Modifier.isStatic(field.getModifiers())) {
						if (BaseTypeUtils.isBaseType(field.getType())) {
							targetValue = BaseTypeUtils.string2BaseType(value, field.getType());
						} else if (String.class.equals(field.getType())) {
							targetValue = value;
						} else {
							targetValue = new Gson().fromJson(value, field.getGenericType());
						}
						field.setAccessible(true);
						field.set(result, targetValue);
						flag = true;
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		if (!flag) {
			result = null;
		}

		return result;
	}
	
	public static Method getTargetMethod(Object service, Method sourceMethod) {
		Method realMethod = sourceMethod;
		Method[] methodList = AopUtils.getTargetClass(service).getMethods();
		for (int i = methodList.length - 1; i >= 0; --i) {
			if (!Arrays.asList(methodList[i].getParameterTypes()).contains(Object.class)
					&& methodList[i].getName().equals(sourceMethod.getName())
					&& methodList[i].getParameterCount() == sourceMethod.getParameterCount()) {

				boolean flag = false;
				Class[] sourceClsArr = sourceMethod.getParameterTypes();
				Class[] destClsArr = methodList[i].getParameterTypes();
				for (int j = 0; j < sourceClsArr.length; ++j) {
					if (!sourceClsArr[j].isAssignableFrom(destClsArr[j])) {
						flag = true;
						break;
					}
				}

				if (!flag) {
					realMethod = methodList[i];
					break;
				}
			}
		}

		return realMethod;
	}
		 
	public static boolean isParameterizedType(Object beanService) {
		boolean flag = false;
		Type[] types = AopUtils.getTargetClass(beanService).getGenericInterfaces();
		Type[] arg2 = types;
		int arg3 = types.length;
		for (int arg4 = 0; arg4 < arg3; ++arg4) {
			Type type = arg2[arg4];
			if (type instanceof ParameterizedType) {
				flag = true;
				break;
			}
		}
		return flag;
	}
	
//	public static Method getInvokeMethod(Object target, String methodName) {
//		String key = String.format("class:%s_method:%s_param:%s,%s", new Object[] { ServiceUtils.class.getName(),
//				"getInvokeMethod", target.getClass().getName(), methodName });// 57 58
//		Method result = (Method) StaticCache.getInstance().get(key);// 59
//		if (result == null) {// 60
//			List methodList = Helper.getAllInterfaceMethods(target);// 62
//			Iterator arg4 = methodList.iterator();// 65
//
//			while (arg4.hasNext()) {
//				Method method = (Method) arg4.next();
//				if (methodName.equals(method.getName())) {// 66
//					result = method;// 69
//					StaticCache.getInstance().put(key, method);// 70
//					break;// 71
//				}
//			}
//		}
//
//		return result;// 75
//	}
		 
	public static void main(String[] args) throws NoSuchMethodException, SecurityException {
		  ParameterNameDiscoverer parameterNameDiscoverer =   
	                new LocalVariableTableParameterNameDiscoverer();  
	    
	            String[] parameterNames = parameterNameDiscoverer  
	                    .getParameterNames(MessageServiceImpl.class.getDeclaredMethod("findByCondition",  
	                            String.class));  
	            System.out.print("test : ");  
	            for (String parameterName : parameterNames) {  
	                System.out.print(parameterName + ' ');  
	            }  
	        
		Method method = MessageServiceImpl.class.getMethod("findByCondition",String.class); 
		Parameter[] types = method.getParameters(); 
		String parameterName = types[0].getName(); 
		System.out.println("parameterName:"+parameterName);
		
	}
	
	public static Method getInvokeMethod(Object bean,String methodName){
		String key = String.format("class:%s_method:%s_param:%s,%s", new Object[] { ServiceUtils.class.getName(),
				"getInvokeMethod", bean.getClass().getName(), methodName });
		logger.info("key:"+key);
		Method realMethod = null;
		realMethod = (Method) StaticCache.getInstance().get(key);
		if(null == realMethod){
			List<Method> listMethod = HelperUtils.getAllInterfaceMethods(bean.getClass());
			Iterator<Method> iterator = listMethod.iterator();
			while(iterator.hasNext()){
				Method method = (Method) iterator.next();
				if(method.getName().equals(methodName)){
					realMethod = method;
					StaticCache.getInstance().put(key, method);
					break;
				}
			}
		}
		return realMethod;
	}
	
	/**
	 * 权限判断
	 * @return
	 */
	public static boolean hasPermission(){
		Session session = CurrentSessionStoreFactory.getCurrentSessionStore().getCurrentSession();
		if(null == session){
			logger.info("未登录！");
			return false;
		}
		Subject subject = new Subject.Builder().session(session).buildSubject();
		if(!subject.isAuthenticated()){
			logger.info("未认证！");
			return false;
		}
		return true;
	}
}
