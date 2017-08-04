package rpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import proxy.CGLIBDynamicProxy;

public class RPCFramework {
	/**
	 * 暴露服务
	 * 
	 * @param service
	 * @param ip
	 * @param port
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static void export(List<Object> serviceList, int port) throws IOException {
		ServerSocket server = new ServerSocket(port);
		for (;;) {
			for (Object service : serviceList) {
				if (null == service) {
					throw new IllegalArgumentException("service instance == null");
				}
				if (port <= 0 || port > 65535) {
					throw new IllegalArgumentException("Invalid port " + port);
				}
				System.out.println("Export service " + service.getClass().getName() + " on port " + port);
				
				Socket socket = server.accept();
				System.out.println("Export service " + service.getClass().getName() + " on port " + port);
				new Thread(new Runnable() {
					public void run() {
						ObjectInputStream input = null;
						ObjectOutputStream output = null;
						try {
							input = new ObjectInputStream(socket.getInputStream());
							// 方法名
							String methodName = input.readUTF();
							Class<?>[] parameTypes = (Class<?>[]) input.readObject();
							Object[] args = (Object[]) input.readObject();
							output = new ObjectOutputStream(socket.getOutputStream());
							Method method = service.getClass().getMethod(methodName, parameTypes);
							Object result = method.invoke(service, args);
							output.writeObject(result);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} finally {
							try {
								output.close();
								input.close();
								socket.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}).start();
			}

		}
	}

	/**
	 * jdk动态代理
	 * @param interfaceClass
	 * @param host
	 * @param port
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T refer(final Class<T> interfaceClass, final String host, final int port) {
		if (null == interfaceClass) {
			throw new IllegalArgumentException("Interface class == null");
		}
		if (!interfaceClass.isInterface()) {
			throw new IllegalArgumentException("The " + interfaceClass.getName() + " must be interface class!");
		}
		Object obj = Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[] {interfaceClass},
				new InvocationHandler() {

					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						System.out.println("begin,mehotd["+method+"],args["+args+"].............");
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
							System.out.println("result:"+result);
							System.out.println("end,mehotd["+method+"],args["+args+"].............");
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							input.close();
							output.close();
							socket.close();
						}
						return result;
					}
				});
		return (T) obj;
	}
	
	/**
	 * cglib动态代理
	 * @param service
	 * @param host
	 * @param port
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T cglibRefer(Object service,String host, final int port) {
		CGLIBDynamicProxy proxy = new CGLIBDynamicProxy();
		Object obj = proxy.getInstance(service,host,port);
		return (T) obj;
	}
	
	/**
	 * cglib动态代理
	 * @param interfaceClass
	 * @param host
	 * @param port
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T cglibReferByClass(Class<T> interfaceClass,String host, final int port) {
		CGLIBDynamicProxy proxy = new CGLIBDynamicProxy();
		Object obj = proxy.getInstanceByClass(interfaceClass,host,port);
		return (T) obj;
	}
}
