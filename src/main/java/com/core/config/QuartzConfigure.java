package com.core.config;


import javax.inject.Singleton;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;


/**
 * quartz 配置类
 *
 * @author xzw
 */
@Configuration
public class QuartzConfigure {
	
  
    /**
     * 
     * @return
     */
    @Bean(name="schedulerFactoryBean")
    @Singleton
    public SchedulerFactoryBean getSchedulerFactoryBean(){
    	return new SchedulerFactoryBean(); 
    }
}
