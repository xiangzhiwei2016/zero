package proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.core.JdkVersion;

public class Client {
	public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		//代理的真实对象
		Subject real = new RealSubject();
		Object obj = new JDKDynamicProxy().bind(real);
		Subject s = (Subject)obj;
		s.say("ssfdf");
		Method[] ml = obj.getClass().getDeclaredMethods();
//		for(Method m : ml){
//			Object obj2 = m.invoke(obj, "sss");
//			System.out.println(obj2);
//		}
//		Method m = obj.getClass().getDeclaredMethod("say", String.class);
//		Object obj2 = m.invoke(obj, ",sdfsdf");
//		System.out.println(obj2);
		//-------------cglib-------------//
//		RealSubject s2 = (RealSubject)new CGLIBDynamicProxy().getInstance(new RealSubject(),"1",1);
//		System.out.println(s2.say("ddsds"));
		
		JdkVersion jdkVersion = new JdkVersion() {  
        };  
        System.out.println(jdkVersion.getJavaVersion());   
	}
}
