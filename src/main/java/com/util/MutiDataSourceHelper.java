package com.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.configure.SystemConfigProperties;

public class MutiDataSourceHelper {

	private static final String DATASOURCE_MUTI_PRO_NAME = "datasource.multiple";
	private static final String DATASOURCE_MUTI_SEPARATOR = ";";
	private static final String DATASOURCE_MUTI_SEPARATOR_PACKAGE = ":";
	private static Map<String, String> packageDataSourceMap = null;
	private static Map<String, String> dataSourceDbTypeMap = new HashMap();

	public static String[][] getDataSourceDeploy() {
		String datasources = SystemConfigProperties.getProperty("datasource.multiple", "").trim();
		if ("".equals(datasources)) {
			return new String[0][0];
		} else {
			String[] datasourceArrayTmp = datasources.split(";");
			if (datasourceArrayTmp.length == 0) {
				return new String[0][0];
			} else {
				String[][] datasourceArray = new String[datasourceArrayTmp.length][];

				for (int i = 0; i < datasourceArrayTmp.length; ++i) {
					String[] packages = datasourceArrayTmp[i].split(":");
					if (packages == null || packages.length != 2) {
						throw new RuntimeException("多数据源配置不正确：" + datasources);
					}

					datasourceArray[i] = packages;
				}

				return datasourceArray;
			}
		}
	}

	public static String[] getDataSourceNames() {
		String[][] dataSourceArray = getDataSourceDeploy();
		if (dataSourceArray.length == 0) {
			return new String[0];
		} else {
			String[] sourceName = new String[dataSourceArray.length];

			for (int i = 0; i < dataSourceArray.length; ++i) {
				sourceName[i] = dataSourceArray[i][0];
			}

			return sourceName;
		}
	}

	public static Map<String, String> getPackageDataSourceMap() {
		if (packageDataSourceMap != null) {
			return packageDataSourceMap;
		} else {
			Class arg = MutiDataSourceHelper.class;
			synchronized (MutiDataSourceHelper.class) {
				if (packageDataSourceMap != null) {
					return packageDataSourceMap;
				} else {
					String[][] packagesArray = getDataSourceDeploy();
					packageDataSourceMap = new HashMap();
					if (packagesArray.length == 0) {
						return packageDataSourceMap;
					} else {
						for (int i = 0; i < packagesArray.length; ++i) {
							packageDataSourceMap.put(packageNameParseToRegex(packagesArray[i][1]), packagesArray[i][0]);
						}

						return packageDataSourceMap;
					}
				}
			}
		}
	}

	private static String packageNameParseToRegex(String packageName) {
		String result = packageName.replaceAll("\\.", "\\\\.");
		result = result.replaceAll("\\\\.\\\\.", "\\\\.[\\\\w\\\\d\\\\.]+");
		result = result.replaceAll("\\*", "[\\\\w\\\\d]+");
		return result;
	}

	public static String getPackageDataSource(String packageName) {
		Map packagesMap = getPackageDataSourceMap();
		Iterator arg1 = packagesMap.keySet().iterator();

		String packName;
		do {
			if (!arg1.hasNext()) {
				return null;
			}

			packName = (String) arg1.next();
		} while (!packageName.matches(packName));

		return (String) packagesMap.get(packName);
	}

	public static String getDbType(String dataSourceName) {
		String value = (String) dataSourceDbTypeMap.get(dataSourceName);
		if (value == null || "".equals(value)) {
			value = "MYSQL";
		}

		return value;
	}

	public static void addDataSourceNameDbType(String dataSourceName, String dbType) {
		dataSourceDbTypeMap.put(dataSourceName, dbType);
	}

}
