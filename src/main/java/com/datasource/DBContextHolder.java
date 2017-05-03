package com.datasource;

import com.util.MutiDataSourceHelper;

public class DBContextHolder {
	private static final ThreadLocal<String> contextHolder = new ThreadLocal();
	private static boolean isMultiDatasouce = true;

	public static String getDBType() {
		return (String) contextHolder.get();
	}

	public static void setDBType(String dbType) {
		contextHolder.set(dbType);
	}

	public static void clearDBType() {
		contextHolder.remove();
	}

	public static void setDBTypeByService(String className, String funName) {
		if (isMultiDatasouce) {
			StringBuilder callPath = new StringBuilder();
			callPath.append(className).append(".").append(funName);
			setDBType(MutiDataSourceHelper.getPackageDataSource(callPath.toString()));
		}

	}

	static {
		if (MutiDataSourceHelper.getPackageDataSourceMap().keySet().size() == 0) {
			isMultiDatasouce = false;
		}

	}
}