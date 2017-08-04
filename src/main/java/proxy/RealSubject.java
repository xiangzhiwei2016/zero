package proxy;

public class RealSubject implements Subject{

	@Override
	public String say(String hello) {
		System.out.println("hello,word"+hello);
		return "hello,word"+hello;
	}

}
