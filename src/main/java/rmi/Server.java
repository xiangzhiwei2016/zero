package rmi;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/* 
* Context接口表示一个命名上下文，它由一组名称到对象的绑定组成。 
* 它包含检查和更新这些绑定的一些方法。 
*/ 
import javax.naming.Context; 
/* 
* InitialContext类是执行命名操作的初始上下文。    
* 该初始上下文实现 Context 接口并提供解析名称的起始点。 
*/ 
import javax.naming.InitialContext; 
public class Server { 
  public static void main(String[] args) { 
    try { 
      //实例化实现了IService接口的远程服务ServiceImpl对象 
      IService service02 = new ServiceImpl("service02"); 
      LocateRegistry.createRegistry(8888); 

      //把远程对象注册到RMI注册服务器上，并命名为RHello 
      //绑定的URL标准格式为：rmi://host:port/name(其中协议名可以省略，下面两种写法都是正确的） 
      Naming.bind("rmi://localhost:8888/service02",service02); 
    } catch (Exception e) { 
      e.printStackTrace(); 
    } 
    System.out.println("服务器向命名表注册了1个远程服务对象！"); 
  } 
}