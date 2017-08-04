package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


/**
 * 动态代理类
 * @author xiangzhiwei
 *
 */
public class JDKDynamicProxy implements InvocationHandler{
    //　这个就是我们要代理的真实对象
    private Object subject;
    
    //    构造方法，给我们要代理的真实对象赋初值
    public Object bind(Object subject)
    {
        this.subject = subject;
        return Proxy.newProxyInstance(subject.getClass().getClassLoader(), subject.getClass().getInterfaces(), this);
    }

	@Override
	public Object invoke(Object arg0, Method arg1, Object[] arg2) throws Throwable {
		Object object = null;
		System.out.println("begin,...................");
		object = arg1.invoke(subject, arg2);
		System.out.println("end,...................");
		return object;
	}

}
