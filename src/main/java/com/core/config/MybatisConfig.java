package com.core.config;

import java.io.IOException;
import javax.inject.Singleton;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.configure.SystemConfigProperties;
import com.mchange.v2.c3p0.ComboPooledDataSource;

@EnableTransactionManagement
@Configuration
@AutoConfigureAfter(DataSourceConfig.class)
public class MybatisConfig {
	private static final Logger logger = LoggerFactory.getLogger(MybatisConfig.class);
	
	@Autowired
	@Lazy
	SqlSessionFactoryBean sqlSessionFactoryBean;
	
	@Autowired(required = true)
	DataSource dataSource;
	
	@Bean(name = { "sqlSessionFactory" })
	@Singleton
	public SqlSessionFactoryBean getSqlSessionFactoryBean() throws IOException {
		logger.info("mybatis:3");
		SqlSessionFactoryBean sql = new SqlSessionFactoryBean();
		ComboPooledDataSource ds = new ComboPooledDataSource("sso");
		sql.setDataSource(ds);
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource[] resources = resolver.getResources("classpath*:com/**/*Mapper.xml");
		sql.setMapperLocations(resources);
		Resource[] resources2 = resolver.getResources("classpath*:mybatis-config.xml");
		if (resources2.length > 0) {
			sql.setConfigLocation(resources2[0]);
		}
		logger.info("mybatis:3end");
		sqlSessionFactoryBean= sql;
		return sql;
	}

	@Bean
	@Singleton
	public MapperScannerConfigurer gtMapperScannerConfigurer() {
		logger.info("mybatis:1");
		MapperScannerConfigurer msc = new MapperScannerConfigurer();
		String basePackage = SystemConfigProperties.getProperty("mybatis.basePackage", "com.**.mapper");
		msc.setBasePackage(basePackage);
		logger.info("msc:"+msc.toString());
		msc.setSqlSessionFactoryBeanName("sqlSessionFactory");
		return msc;
	}

	@Bean
	public SqlSession getSqlSession() {
		logger.info("mybatis:2");
		try {
			return this.sqlSessionFactoryBean.getObject().openSession();
		} catch (Exception arg1) {
			throw new RuntimeException(arg1);
		}
	}
	
	public static void main(String[] args) {
		AnnotationConfigApplicationContext cx = new AnnotationConfigApplicationContext();
		cx.refresh();
		System.out.println(cx.getBean(""));
	}
}
