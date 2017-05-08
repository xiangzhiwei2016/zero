package com.jpaconfig;



import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;
import javax.inject.Inject;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.configure.SystemConfigProperties;

@EnableTransactionManagement
@EnableJpaRepositories(basePackages = { "com" }, repositoryFactoryBeanClass = FrameworkRepoFactorybean.class)
@Configuration
public class JpaConfig {
	private static final Log logger = LogFactory.getLog(JpaConfig.class);
	private static final String HIBERNATE_SHOW_SQL = "hibernate.showsql";
	private static final String HIBERNATE_DATABASE = "hibernate.database";
	@Inject
	@Lazy
	LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = null;
	@Inject
	ApplicationContext context = null;
	@Autowired(required = true)
	DataSource dataSource = null;

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory()
			throws SQLException, PropertyVetoException, IOException {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabase(Database.valueOf(SystemConfigProperties.getProperty(HIBERNATE_DATABASE, "MYSQL")));
		vendorAdapter.setShowSql(
				Boolean.valueOf(SystemConfigProperties.getProperty(HIBERNATE_SHOW_SQL, "false")).booleanValue());
		vendorAdapter.setGenerateDdl(true);
		String hibernateAuto = SystemConfigProperties.getProperty("hibernate.hbm2ddl.auto", "").trim();
		logger.info("hibernateAuto:"+hibernateAuto);
		if (hibernateAuto != null && !hibernateAuto.equals("")) {
			hibernateAuto = "update";
			factory.getJpaPropertyMap().put("hibernate.hbm2ddl.auto", hibernateAuto);
			factory.getJpaPropertyMap().put("hibernate.format_sql",
					SystemConfigProperties.getProperty("hibernate.format_sql", "false"));
		} else {
			vendorAdapter.setGenerateDdl(false);
		}

		String l2cache = SystemConfigProperties.getProperty("hibernate.cache.use_second_level_cache", "false");
		factory.getJpaPropertyMap().put("hibernate.cache.use_second_level_cache", l2cache);
		if ("true".equals(l2cache)) {
			factory.getJpaPropertyMap().put("hibernate.cache.use_query_cache",
					SystemConfigProperties.getProperty("hibernate.cache.use_query_cache", "false"));
			factory.getJpaPropertyMap().put("hibernate.generate_statistics",
					SystemConfigProperties.getProperty("hibernate.generate_statistics", "false"));
			String regonfactory = SystemConfigProperties.getProperty("hibernate.cache.region.factory_class", "");
			factory.getJpaPropertyMap().put("hibernate.cache.region.factory_class", regonfactory);
//			if (regonfactory.indexOf(ProviderEnum.ignite.name()) != -1) {
//				FrameworkInitConfigure.igniteInit();
//				factory.getJpaPropertyMap().put("org.apache.ignite.hibernate.grid_name",
//						SystemConfigProperties.getProperty("ignite.l2cache.grid_name", "framework-grid"));
//				factory.getJpaPropertyMap().put("org.apache.ignite.hibernate.default_cache",
//						SystemConfigProperties.getProperty("ignite.l2cache.default_cache", "readWriteRegionCache"));
//				factory.getJpaPropertyMap().put("org.apache.ignite.hibernate.default_access_type",
//						SystemConfigProperties.getProperty("ignite.l2cache.default_access_type", "READ_WRITE"));
//			}
		}

		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan(new String[] { SystemConfigProperties.getProperty("basebackage.scan", "com") });
		factory.setDataSource(this.dataSource);
		logger.info("japConfig:datasource:"+dataSource);
		return factory;
	}

	@Bean
	public PlatformTransactionManager transactionManager() throws SQLException {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(this.localContainerEntityManagerFactoryBean.getObject());
		return txManager;
	}
}