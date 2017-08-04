package rpc;

import java.lang.reflect.Method;

import proxy.Subject;

public class Consumer {
	 public static void main(String[] args) throws Exception {  
	        Object service = RPCFramework.refer(HelloService.class, "127.0.0.1", 1235);  
	        Object service2 = RPCFramework.refer(Subject.class, "127.0.0.1", 1235);  
	        System.out.println(service2.getClass().getName());
//	        Method method = service.getClass().getDeclaredMethod("say",String.class);
//	        System.out.println(method.getName());
	        Subject ser = (Subject)service2;
	        Object o = ser.say("hello");
	        System.out.println(o);
//	        for (int i = 0; i < Integer.MAX_VALUE; i ++) {  
//	            System.out.println(".........................");
//		        Object result = method.invoke(service, "word"+i);
//		        System.out.println(".........................");
//		        System.out.println(result);
//	            Thread.sleep(1000);  
//	        }  
	    }  
}
