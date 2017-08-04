package rpc;

import java.util.ArrayList;
import java.util.List;

import proxy.RealSubject;
import proxy.Subject;

public class Provider {
	public static void main(String[] args) throws Exception {  
        HelloService service1 = new HelloServiceImpl(); 
        Subject subject = new RealSubject(); 
        List<Object> list1 = new ArrayList<Object>();
        List<Object> list2 = new ArrayList<Object>();
        list1.add(service1);
        list1.add(subject);
//        RPCFramework.export(service1, 1235);
        RPCFramework.export(list1, 1235);
    }  
}
