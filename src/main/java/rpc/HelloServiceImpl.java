package rpc;

public class HelloServiceImpl implements HelloService{

	@Override
	public String say(String hello) {
		System.out.println("hello,"+hello);
		return hello;
	}

}
