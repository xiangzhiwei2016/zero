package com.zero.dubbo.config;

import java.lang.annotation.Annotation;
import java.util.Set;
import static org.springframework.util.Assert.notNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.alibaba.dubbo.config.spring.ReferenceBean;
import com.alibaba.dubbo.config.spring.ServiceBean;
import com.zero.dubbo.annotation.Gateway;

import utils.PsUtil;

public class DubboReferenceScanerConfig
implements BeanDefinitionRegistryPostProcessor, InitializingBean, ApplicationContextAware, BeanNameAware {
	
	private String basePackage;
	
	private Class<? extends Annotation> annotationClass;
	
	private ApplicationContext applicationContext;
	
	private Class<?> markerInterface;
	
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory arg0) throws BeansException {
	
	}

	@Override
	public void setBeanName(String arg0) {
			
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		this.applicationContext =  arg0;
	}

	public ApplicationContext getApplicationContext() throws BeansException {
		return this.applicationContext;
	}
	@Override
	public void afterPropertiesSet() throws Exception {
		notNull(PsUtil.convertToNull(this.basePackage), "Property 'basePackage' is required");	
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry arg0) throws BeansException {
		DubboScanner scanner = new DubboScanner(arg0);
		scanner.setBeanClass(ReferenceBean.class);
		scanner.setAnnotationClass(Gateway.class);
		scanner.setResourceLoader(getApplicationContext());
		scanner.setMarkerInterface(getMarkerInterface());
		 scanner.setBeanNameGenerator(new BeanNameGenerator(){
				@Override
				public String generateBeanName(BeanDefinition definition,BeanDefinitionRegistry registry) {
					String beanName = definition.getBeanClassName();				
		            int counter = 2;
		            int depth = 10000;
		            while(registry.containsBeanDefinition(beanName)
		            		&&(depth--<0) ) {
		            	beanName = beanName + (counter ++);
		            }				
					return beanName;
				}
		    	
		    });
		scanner.registerFilters();
		Set<BeanDefinitionHolder> set = scanner.doScan(getBasePackage());
		
		System.out.println(getBasePackage()+"Reference:"+set.toString());
	}

	public String getBasePackage() {
		return basePackage;
	}

	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

	public Class<? extends Annotation> getAnnotationClass() {
		return annotationClass;
	}

	public void setAnnotationClass(Class<? extends Annotation> annotationClass) {
		this.annotationClass = annotationClass;
	}

	public Class<?> getMarkerInterface() {
		return markerInterface;
	}

	public void setMarkerInterface(Class<?> markerInterface) {
		this.markerInterface = markerInterface;
	}

}
