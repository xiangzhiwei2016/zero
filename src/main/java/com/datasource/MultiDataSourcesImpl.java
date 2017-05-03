package com.datasource;


import java.util.HashMap;
import java.util.Map;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.sql.DataSource;

@Named
@Singleton
public class MultiDataSourcesImpl implements MultiDataSources {
	private Map<Object, Object> dataSourceMap = new HashMap();

	public DataSource getDataSource(String datasourceName) {
		return (DataSource) this.dataSourceMap.get(datasourceName);
	}

	public void setDataSourceMap(Map<Object, Object> dataSourceMap) {
		this.dataSourceMap = dataSourceMap;
	}
}