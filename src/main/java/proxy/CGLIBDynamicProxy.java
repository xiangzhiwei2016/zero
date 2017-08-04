package proxy;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

public class CGLIBDynamicProxy implements MethodInterceptor{
    private Object target;  
    
    private String host;
    
    private int port;
    
    /** 
     * 创建代理对象 
     *  
     * @param target 
     * @return 
     */  
    public Object getInstance(Object target,String host,int port) {  
        this.target = target;  
        this.host = host;
        this.port = port;
        Enhancer enhancer = new Enhancer();  
        enhancer.setSuperclass(this.target.getClass());  
        // 回调方法  
        enhancer.setCallback(this);  
        // 创建代理对象  
        return enhancer.create();  
    }  
    
    /** 
     * 创建代理对象 
     *  
     * @param clazz 
     * @return 
     */  
    public Object getInstanceByClass(Class<?> clazz,String host,int port) {  
        this.host = host;
        this.port = port;
        Enhancer enhancer = new Enhancer();  
        enhancer.setSuperclass(clazz);  
        // 回调方法  
        enhancer.setCallback(this);  
        // 创建代理对象  
        return enhancer.create();  
    }  
	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
//		Object object  = null;
//		System.out.println("cglib,begin...................");
//		object = proxy.invokeSuper(obj, args);
//		System.out.println("cglib,end...................");

		System.out.println("begin,mehotd["+method.getName()+"],args["+args.toString()+"].............");
		Object result = null;
		Socket socket = null;
		ObjectOutputStream output = null;
		ObjectInputStream input = null;
		try {
			socket = new Socket(host, port);
			output = new ObjectOutputStream(socket.getOutputStream());
			output.writeUTF(method.getName());
			output.writeObject(method.getParameterTypes()); 
			output.writeObject(args);

			input = new ObjectInputStream(socket.getInputStream());
			result = input.readObject();
			if (result instanceof Throwable) {
				throw (Throwable) result;
			}
			System.out.println("end,mehotd["+method.getName()+"],args["+args.toString()+"].............");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			input.close();
			output.close();
			socket.close();
		}
		return result;
	}

}
