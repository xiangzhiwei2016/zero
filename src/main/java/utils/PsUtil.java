package utils;

import org.apache.commons.lang3.StringUtils;

public class PsUtil {

	public static String convertToNull(String str) {
		if (StringUtils.isBlank(str)) {
			return null;
		}
		return StringUtils.trim(str);
	}
	
	public static String convertToString(Object obj) {
		return obj== null ? "":obj.toString();
	}
}
