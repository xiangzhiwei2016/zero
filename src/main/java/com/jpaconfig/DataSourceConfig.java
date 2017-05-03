package com.jpaconfig;

import com.datasource.DynamicDataSource;
import com.datasource.MultiDataSources;

/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/


import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.util.MutiDataSourceHelper;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class DataSourceConfig {
	private static final Log logger = LogFactory.getLog(DataSourceConfig.class);
	@Inject
	@Lazy
	private MultiDataSources multiDataSources = null;

	private DataSource multiDataSource(String name) throws SQLException, PropertyVetoException, IOException {
		ComboPooledDataSource ds = new ComboPooledDataSource(name);
		if (ds.getDriverClass().indexOf("mysql") != -1) {
			MutiDataSourceHelper.addDataSourceNameDbType(name, "MYSQL");
		} else if (ds.getDriverClass().indexOf("oracle") != -1) {
			MutiDataSourceHelper.addDataSourceNameDbType(name, "ORACLE");
		} else {
			MutiDataSourceHelper.addDataSourceNameDbType(name, "MYSQL");
		}

		logger.info("Datasource: " + name + "," + ds.getJdbcUrl());
		ds.getConnection().close();
		return ds;
	}

	@Bean
	@Singleton
	public DataSource dataSource() throws PropertyVetoException, SQLException, IOException {
		DynamicDataSource ds = new DynamicDataSource();
		String[] dataSourcenames = MutiDataSourceHelper.getDataSourceNames();
		HashMap dataSourceMap = new HashMap();
		if (dataSourcenames.length == 0) {
			dataSourceMap.put((Object) null, this.multiDataSource((String) null));
			ds.setTargetDataSources(dataSourceMap);
			return ds;
		} else {
			for (int i = 0; i < dataSourcenames.length; ++i) {
				dataSourceMap.put(dataSourcenames[i], this.multiDataSource(dataSourcenames[i]));
			}

			dataSourceMap.put((Object) null, this.multiDataSource((String) null));
			ds.setTargetDataSources(dataSourceMap);
			this.multiDataSources.setDataSourceMap(dataSourceMap);
			return ds;
		}
	}
}