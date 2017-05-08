package com.jpaconfig;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.configure.SystemConfigProperties;
import com.datasource.MultiDataSources;

@EnableTransactionManagement
@Configuration
public class MybatisConfig {
//	private static final Logger logger = LoggerFactory.getLogger(MybatisConfig.class);
//	
//	@Autowired(required = true)
//	DataSource dataSource = null;
//	
//	@Autowired
//	@Lazy
//	SqlSessionFactoryBean sqlSessionFactoryBean;
//	
//	@Inject
//	private MultiDataSources multiDataSources = null;
//
//	@Bean(name = { "sqlSessionFactory" })
//	@Singleton
//	public SqlSessionFactoryBean getSqlSessionFactoryBean() throws IOException {
//		SqlSessionFactoryBean sql = new SqlSessionFactoryBean();
//		logger.info("dataSource:"+dataSource);
//		DataSource d = multiDataSources.getDataSource("sso");
//		logger.info("d:"+d);
//		sql.setDataSource(this.dataSource);
//		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//		Resource[] resources = resolver.getResources("classpath*:com/**/*Mapper.xml");
//		sql.setMapperLocations(resources);
//		Resource[] resources2 = resolver.getResources("classpath*:mybatis-config.xml");
//		if (resources2.length > 0) {
//			sql.setConfigLocation(resources2[0]);
//		}
//
//		return sql;
//	}
//
//	@Bean
//	@Singleton
//	public MapperScannerConfigurer gtMapperScannerConfigurer() {
//		logger.info("mybatis:1");
//		MapperScannerConfigurer msc = new MapperScannerConfigurer();
//		String basePackage = SystemConfigProperties.getProperty("mybatis.basePackage", "com.**.mapper");
//		msc.setBasePackage(basePackage);
//		logger.info("msc:"+msc.toString());
//		msc.setSqlSessionFactoryBeanName("sqlSessionFactory");
//		return msc;
//	}
//
//	@Bean
//	public SqlSession getSqlSession() {
//		logger.info("mybatis:2");
//		try {
//			return this.sqlSessionFactoryBean.getObject().openSession();
//		} catch (Exception arg1) {
//			throw new RuntimeException(arg1);
//		}
//	}
}
