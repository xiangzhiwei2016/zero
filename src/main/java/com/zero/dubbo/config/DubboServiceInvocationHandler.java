package com.zero.dubbo.config;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.springframework.context.ApplicationContext;
  
/**
 * dubbo服务ref代理
 * @author marshal.liu
 * @date 2015年9月1日 下午3:16:54
 * @version 1.0
 */
public class DubboServiceInvocationHandler implements InvocationHandler {  
    
	private transient ApplicationContext applicationContext;
	
	/**
	 * 代理接口
	 */
    private Class<?> interfaceClass;
    
    /**
     * dubbo服务ref
     */
    private String refName;       
      
    public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public void setInterfaceClass(Class<?> interfaceClass) {
		this.interfaceClass = interfaceClass;
	}

	public void setRefName(String refName) {
		this.refName = refName;
	}

	public DubboServiceInvocationHandler(){
		super() ;  
	}
	/** 
     * 构造方法 
     * @param interfaceClass 代理接口 
     * @param refName dubbo服务ref
     */  
    public DubboServiceInvocationHandler(Class<?> interfaceClass,String refName) {  
        super() ;  
        this.interfaceClass = interfaceClass ;
        this.refName = refName;
    }  
      
    /** 
     * 重写执行目标对象的方法 
     */  
    @Override  
    public Object invoke(Object proxy, Method method, Object[] args)  
            throws Throwable {            
        Object target = applicationContext.getBean(refName); 
        // 执行目标对象方法  
        Object result = method.invoke(target, args);                      
        return result;  
    }  
      
    /** 
     * 获取目标对象的代理对象 
     * @return 代理对象 
     */  
    public Object getProxy(){  
        return Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, this);  
    }            
}  