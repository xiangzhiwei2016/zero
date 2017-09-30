package com.dubbo.config;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

import com.alibaba.dubbo.config.spring.ServiceBean;


/**
 * 服务提供方
 * 扫描
 * @author xiangzhiwei
 *
 */
public class DubboServiceScanner extends ClassPathBeanDefinitionScanner{
	private static final Logger logger = LoggerFactory.getLogger(DubboServiceScanner.class);
	private Class<? extends Annotation> annotationClass;

	private Class<?> markerInterface;

	private Class<?> beanClass;
	
	private Properties properties;


	public void setAnnotationClass(Class<? extends Annotation> annotationClass) {
		this.annotationClass = annotationClass;
	}

	public void setMarkerInterface(Class<?> markerInterface) {
		this.markerInterface = markerInterface;
	}

	public void setBeanClass(Class<?> beanClass) {
		this.beanClass = beanClass;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public DubboServiceScanner(BeanDefinitionRegistry registry) {
		super(registry);
		
	}
	
	public Set<BeanDefinitionHolder> doScan(String... basePackages) {
		Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
		if (beanDefinitions.isEmpty()) {
			// 扫描包下没有
			logger.warn("No Service was found in '" + Arrays.toString(basePackages)
					+ "'package.Please check your configuration!");
		} else {
			try {
				// 不为空
				for (BeanDefinitionHolder holder : beanDefinitions) {
					GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();
					if (logger.isInfoEnabled()) {
						logger.info("Creating DubboServiceFactoryBean with name '" + holder.getBeanName() + "'and '"
								+ definition.getBeanClassName() + "' dubboServiceInterface");
					}
					String beanClassName = definition.getBeanClassName();

					Class<?> interfaceBeanClass = Class.forName(beanClassName);
					definition.getPropertyValues().add("id", beanClassName);
					definition.getPropertyValues().add("interface", beanClassName);
					
					if(null != properties){
						// todo
					}
					definition.setBeanClass(this.beanClass);
					if(this.beanClass.getName().equals(ServiceBean.class.getName())){
						String refName = beanClassName;
						BeanDefinition refBean = getRegistry().getBeanDefinition(refName);
						if(refBean.isSingleton()){
							definition.getPropertyValues().add("ref", new RuntimeBeanReference(refName));
						}else{
							
						}
					}
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return beanDefinitions;
	}
}
