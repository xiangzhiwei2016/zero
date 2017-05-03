package com.datasource;


import java.util.Map;
import javax.sql.DataSource;

public interface MultiDataSources {
	DataSource getDataSource(String arg0);

	void setDataSourceMap(Map<Object, Object> arg0);
}