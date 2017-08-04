package utils;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtilsBean;

import com.google.gson.Gson;

public class Bean2MapUtils {
	// bean 转 map
	public static Map<String, Object> beanToMap(Object obj) {
		Map<String, Object> params = new HashMap<String, Object>(0);
		try {
			PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
			PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
			for (int i = 0; i < descriptors.length; i++) {
				String name = descriptors[i].getName();
				if (!"class".equals(name)) {
					String value = new Gson().toJson(propertyUtilsBean.getNestedProperty(obj, name));
					value = value.replaceAll("\"","");
					// 将null 过滤掉
					if(null != value && !"null".equals(value)){
						params.put(name, value);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return params;
	}
}
