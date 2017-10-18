package utils;
/**
 * 字符串工具类
 * <br>创建日期：2016年11月7日 下午4:19:28
 * <br><b>Copyright 2015 上海量投网络科技有限公司 All Rights Reserved</b>
 * @author xzw
 * @since 1.0
 * @version 1.0
 */
public class StringUtils {
	
	public static String convertToString(Object obj){
		String ret = "";
		try{
			if(obj != null){
				ret = obj.toString();
			}
		}catch(Exception e){
			
		}		
		return ret;
	}

}
