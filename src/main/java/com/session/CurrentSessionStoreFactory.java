package com.session;

import java.util.function.Supplier;

/**
 * 设置当前会话管理器
 * @author xiangzhiwei
 *
 */
public class CurrentSessionStoreFactory {
	/**
	 * 申请storelamda
	 */
	private static Supplier<CurrentSessionStore> supplier = null;
	
	/**
	 * 设置 supplier
	 * @param supplier
	 */
	public static synchronized void setCurrentSessionStoreSupplier(Supplier<CurrentSessionStore> supplier){
		CurrentSessionStoreFactory.supplier = supplier;
	}
	
	public static void init(){
		if(null == supplier){
			setCurrentSessionStoreSupplier(()->{return new ThreadLocalSessionStore();});
		}
	}
	
	/**
	 * 生成 store
	 * @return
	 */
	public static CurrentSessionStore getCurrentSessionStore(){
		if(null == supplier){
			System.out.println("初始化。。。。。。。。。");
			init();
		}
		CurrentSessionStore store = supplier.get();
		return store;
	}
	
}
