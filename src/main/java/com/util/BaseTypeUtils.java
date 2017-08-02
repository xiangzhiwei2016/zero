package com.util;

public class BaseTypeUtils {
	public static boolean isBaseType(Class<?> cls) {
		boolean flag = false;
		if (Byte.TYPE.equals(cls) || Byte.class.equals(cls) || Short.TYPE.equals(cls) || Short.class.equals(cls)
				|| Integer.TYPE.equals(cls) || Integer.class.equals(cls) || Long.TYPE.equals(cls)
				|| Long.class.equals(cls) || Float.TYPE.equals(cls) || Float.class.equals(cls)
				|| Double.TYPE.equals(cls) || Double.class.equals(cls) || Character.TYPE.equals(cls)
				|| Character.class.equals(cls) || Boolean.TYPE.equals(cls) || Boolean.class.equals(cls)) {
			flag = true;
		}

		return flag;
	}

	public static Object string2BaseType(String str, Class<?> cls) {
		Object result = null;
		if (Byte.TYPE.equals(cls)) {
			result = Byte.valueOf(string2Byte(str));
		} else if (Byte.class.equals(cls)) {
			result = string2ByteObj(str);
		} else if (Short.TYPE.equals(cls)) {
			result = Short.valueOf(string2Short(str));
		} else if (Short.class.equals(cls)) {
			result = string2ShortObj(str);
		} else if (Integer.TYPE.equals(cls)) {
			result = Integer.valueOf(string2Int(str));
		} else if (Integer.class.equals(cls)) {
			result = string2Integer(str);
		} else if (Long.TYPE.equals(cls)) {
			result = Long.valueOf(string2Long(str));
		} else if (Long.class.equals(cls)) {
			result = string2LongObj(str);
		} else if (Float.TYPE.equals(cls)) {
			result = Float.valueOf(string2Float(str));
		} else if (Float.class.equals(cls)) {
			result = string2FloatObj(str);
		} else if (Double.TYPE.equals(cls)) {
			result = Double.valueOf(string2Double(str));
		} else if (Double.class.equals(cls)) {
			result = string2DoubleObj(str);
		} else if (Character.TYPE.equals(cls)) {
			result = Character.valueOf(string2Char(str));
		} else if (Character.class.equals(cls)) {
			result = string2Character(str);
		} else if (Boolean.TYPE.equals(cls)) {
			result = Boolean.valueOf(string2Boolean(str));
		} else if (Boolean.class.equals(cls)) {
			result = string2BooleanObj(str);
		}

		return result;
	}

	public static Byte string2ByteObj(String str) {
		Byte result = null;
		if (str != null && !"".equals(str)) {
			result = Byte.valueOf(str);
		}

		return result;
	}

	public static byte string2Byte(String str) {
		byte result = 0;
		if (str != null && !"".equals(str)) {
			result = Byte.parseByte(str);
		}

		return result;
	}

	public static Short string2ShortObj(String str) {
		Short result = null;
		if (str != null && !"".equals(str)) {
			result = Short.valueOf(str);
		}

		return result;
	}

	public static short string2Short(String str) {
		short result = 0;
		if (str != null && !"".equals(str)) {
			result = Short.parseShort(str);
		}

		return result;
	}

	public static Integer string2Integer(String str) {
		Integer result = null;
		if (str != null && !"".equals(str)) {
			result = Integer.valueOf(str);
		}

		return result;
	}

	public static int string2Int(String str) {
		int result = 0;
		if (str != null && !"".equals(str)) {
			result = Integer.parseInt(str);
		}

		return result;
	}

	public static Long string2LongObj(String str) {
		Long result = null;
		if (str != null && !"".equals(str)) {
			result = Long.valueOf(str);
		}

		return result;
	}

	public static long string2Long(String str) {
		long result = 0L;
		if (str != null && !"".equals(str)) {
			result = Long.parseLong(str);
		}

		return result;
	}

	public static Float string2FloatObj(String str) {
		Float result = null;
		if (str != null && !"".equals(str)) {
			result = Float.valueOf(str);
		}

		return result;
	}

	public static float string2Float(String str) {
		float result = 0.0F;
		if (str != null && !"".equals(str)) {
			result = Float.parseFloat(str);
		}

		return result;
	}

	public static Double string2DoubleObj(String str) {
		Double result = null;
		if (str != null && !"".equals(str)) {
			result = Double.valueOf(str);
		}

		return result;
	}

	public static double string2Double(String str) {
		double result = 0.0D;
		if (str != null && !"".equals(str)) {
			result = Double.parseDouble(str);
		}

		return result;
	}

	public static Character string2Character(String str) {
		Character result = null;
		if (str != null && !"".equals(str)) {
			result = Character.valueOf(str.charAt(0));
		}

		return result;
	}

	public static char string2Char(String str) {
		char result = 0;
		if (str != null && !"".equals(str)) {
			result = str.charAt(0);
		}

		return result;
	}

	public static boolean string2Boolean(String str) {
		boolean result = false;
		if (str != null && !"".equals(str)) {
			result = Boolean.parseBoolean(str);
		}

		return result;
	}

	public static Boolean string2BooleanObj(String str) {
		Boolean result = Boolean.valueOf(false);
		if (str != null && !"".equals(str)) {
			result = Boolean.valueOf(str);
		}

		return result;
	}
}