package com.util;

import java.util.HashMap;
import java.util.Map;

import com.configure.SystemConfigProperties;

public class MutiDataSourceHelper
{
  private static final String DATASOURCE_MUTI_PRO_NAME = "datasource.multiple";
  private static final String DATASOURCE_MUTI_SEPARATOR = ";";
  private static final String DATASOURCE_MUTI_SEPARATOR_PACKAGE = ":";
  private static Map<String, String> packageDataSourceMap = null;
  
  private static Map<String, String> dataSourceDbTypeMap = new HashMap<String, String>();
  
  public static String[][] getDataSourceDeploy()
  {
    String datasources = SystemConfigProperties.getProperty(DATASOURCE_MUTI_PRO_NAME, "").trim();
    
    if ("".equals(datasources)) {
      return new String[0][0];
    }
    
    String[] datasourceArrayTmp = datasources.split(DATASOURCE_MUTI_SEPARATOR);
    
    if (datasourceArrayTmp.length == 0) {
      return new String[0][0];
    }
    
    String[][] datasourceArray = new String[datasourceArrayTmp.length][];
    
    for (int i = 0; i < datasourceArrayTmp.length; i++)
    {
      String[] packages = datasourceArrayTmp[i].split(DATASOURCE_MUTI_SEPARATOR_PACKAGE);
      if ((packages == null) || (packages.length != 2)) {
        throw new RuntimeException("多数据源配置不正确：" + datasources);
      }
      
      datasourceArray[i] = packages;
    }
    
    return datasourceArray;
  }
  
  public static String[] getDataSourceNames()
  {
    String[][] dataSourceArray = getDataSourceDeploy();
    
    if (dataSourceArray.length == 0) {
      return new String[0];
    }
    
    String[] sourceName = new String[dataSourceArray.length];
    
    for (int i = 0; i < dataSourceArray.length; i++) {
      sourceName[i] = dataSourceArray[i][0];
    }
    
    return sourceName;
  }
  
  public static Map<String, String> getPackageDataSourceMap()
  {
    if (packageDataSourceMap != null) {
      return packageDataSourceMap;
    }
    
    synchronized (MutiDataSourceHelper.class) {
      if (packageDataSourceMap != null) {
        return packageDataSourceMap;
      }
      
      String[][] packagesArray = getDataSourceDeploy();
      packageDataSourceMap = new HashMap<String, String>();
      
      if (packagesArray.length == 0) {
        return packageDataSourceMap;
      }
      
      for (int i = 0; i < packagesArray.length; i++) {
        packageDataSourceMap.put(packageNameParseToRegex(packagesArray[i][1]), packagesArray[i][0]);
      }
      
      return packageDataSourceMap;
    }
  }
  
  private static String packageNameParseToRegex(String packageName) {
    String result = packageName;
    result = result.replaceAll("\\.", "\\\\.");
    result = result.replaceAll("\\\\.\\\\.", "\\\\.[\\\\w\\\\d\\\\.]+");
    result = result.replaceAll("\\*", "[\\\\w\\\\d]+");
    return result;
  }
  
  public static String getPackageDataSource(String packageName)
  {
    Map<String, String> packagesMap = getPackageDataSourceMap();
    
    for (String packName : packagesMap.keySet()) {
      if (packageName.matches(packName)) {
        return (String)packagesMap.get(packName);
      }
    }
    
    return null;
  }
  
  public static String getDbType(String dataSourceName)
  {
    String value = (String)dataSourceDbTypeMap.get(dataSourceName);
    if ((value == null) || ("".equals(value))) {
      value = "MYSQL";
    }
    return value;
  }
  
  public static void addDataSourceNameDbType(String dataSourceName, String dbType)
  {
    dataSourceDbTypeMap.put(dataSourceName, dbType);
  }
}

