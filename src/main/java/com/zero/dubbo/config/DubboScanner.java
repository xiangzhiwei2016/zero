package com.zero.dubbo.config;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

import com.alibaba.dubbo.config.spring.ServiceBean;
import com.zero.dubbo.annotation.FileGateway;
import com.zero.dubbo.annotation.Gateway;


public class DubboScanner extends ClassPathBeanDefinitionScanner{
	private static final Logger log = LoggerFactory.getLogger(DubboScanner.class);
	private Class<?> beanClass;
	private Class<? extends Annotation> annotationClass;
	
	private Class<?> markerInterface;
	
	public Class<?> getBeanClass() {
		return beanClass;
	}

	public void setBeanClass(Class<?> beanClass) {
		this.beanClass = beanClass;
	}

	public DubboScanner(BeanDefinitionRegistry registry) {
		super(registry,false);
	}

	@Override
	public Set<BeanDefinitionHolder> doScan(String... basePackages){
		Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
		
		for (BeanDefinitionHolder holder : beanDefinitions) {
			GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();

			if (logger.isDebugEnabled()) {
				logger.debug("Creating DubboServiceFactoryBean with name '"
						+ holder.getBeanName() + "' and '"
						+ definition.getBeanClassName()
						+ "' dubboServiceInterface");
			}
			String beanClassName = definition.getBeanClassName();
		
			System.out.println("beanClassName:"+beanClassName);
			try {
			Class<?> interfaceBeanClass = Class.forName(beanClassName);
			definition.getPropertyValues().addPropertyValue("id", definition.getBeanClassName());
			definition.getPropertyValues().addPropertyValue("interface",beanClassName);
			definition.setBeanClass(this.beanClass);	
			if(this.beanClass.getName().equals(ServiceBean.class.getName())){	
				String refName = DubboUtils.getRefName(beanClassName);
				BeanDefinition refBean = getRegistry().getBeanDefinition(refName);
//				if(refBean.isSingleton()){
//					definition.getPropertyValues().addPropertyValue("ref", new RuntimeBeanReference(refName));							
//				}else{
					DubboServiceInvocationHandler handler = new DubboServiceInvocationHandler();
					handler.setApplicationContext((ApplicationContext)getResourceLoader());
					handler.setRefName(refName);
					handler.setInterfaceClass(interfaceBeanClass);									
					definition.getPropertyValues().addPropertyValue("ref", handler.getProxy());	
//				}						
			}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
							
		}		
		printRegistryInfo(beanDefinitions);
	return beanDefinitions;
	}
	private void printRegistryInfo(Set<BeanDefinitionHolder> beanDefinitions){
		if(log.isInfoEnabled()){
			int count = beanDefinitions.size();
			log.info(String.format("registry %s beans.", count));
			StringBuilder sb = new StringBuilder();
			for (BeanDefinitionHolder holder : beanDefinitions) {
				sb.append(holder.getBeanName()).append(",");				
			}
			log.info(String.format("registry beans info:%s", sb.toString()));
			
		}
	}
	@Override
	protected boolean checkCandidate(String beanName,
			BeanDefinition beanDefinition) throws IllegalStateException {
		//boolean flag = super.checkCandidate(beanName, beanDefinition);
		boolean selfCheckflag = this.hasGatewayAnnotation(beanName, beanDefinition);
//	    if(!selfCheckflag){
//	    	logger.warn("Skipping DubboServiceFactoryBean with name '" + beanName
//					+ "' and '" + beanDefinition.getBeanClassName()
//					+ "' dubboServiceInterface"
//					+ ". Bean already defined with the same name!");
//	    }
		if(selfCheckflag&&this.beanClass.getName().equals(ServiceBean.class.getName())){
			String beanClassName = beanDefinition.getBeanClassName();
			String refName = DubboUtils.getRefName(beanClassName);
			if(!getRegistry().containsBeanDefinition(refName)){
				logger.warn("dubbo服务发布没有找到"+refName+"的实现");
				return false;
			}			
		}
		if (selfCheckflag) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 是否有Gateway注解
	 * @param beanName
	 * @param beanDefinition
	 * @return
	 */
	protected boolean hasGatewayAnnotation(String beanName,
			BeanDefinition beanDefinition){
		try{
			String beanClassName = beanDefinition.getBeanClassName();
			Class<?> beanClass = Class.forName(beanClassName);
			Gateway[] gwArr = beanClass.getDeclaredAnnotationsByType(Gateway.class);
			FileGateway[] fgwArr = beanClass.getDeclaredAnnotationsByType(FileGateway.class);
			if (gwArr != null && gwArr.length>0
				 && (fgwArr==null||fgwArr.length==0)) {
				return true;
			}           
		}catch(ClassNotFoundException ex){
			throw new RuntimeException(ex);
		}
		return false;
	}
	
	@Override
	protected boolean isCandidateComponent(
			AnnotatedBeanDefinition beanDefinition) {
		return (beanDefinition.getMetadata().isInterface() && beanDefinition
				.getMetadata().isIndependent());
	}


	public void registerFilters() {
		boolean acceptAllInterfaces = true;

		// if specified, use the given annotation and / or marker interface
		if (this.annotationClass != null) {
			addIncludeFilter(new AnnotationTypeFilter(this.annotationClass));
			acceptAllInterfaces = false;
		}

		// override AssignableTypeFilter to ignore matches on the actual marker
		// interface
		if (this.markerInterface != null) {
			addIncludeFilter(new AssignableTypeFilter(this.markerInterface) {
				@Override
				protected boolean matchClassName(String className) {
					return false;
				}
			});
			acceptAllInterfaces = false;
		}

		if (acceptAllInterfaces) {
			// default include filter that accepts all classes
			addIncludeFilter(new TypeFilter() {
				public boolean match(MetadataReader metadataReader,
						MetadataReaderFactory metadataReaderFactory)
						throws IOException {
					return true;
				}
			});
		}

		// exclude package-info.java
		addExcludeFilter(new TypeFilter() {
			public boolean match(MetadataReader metadataReader,
					MetadataReaderFactory metadataReaderFactory)
					throws IOException {
				String className = metadataReader.getClassMetadata()
						.getClassName();
				return className.endsWith("package-info");
			}
		});
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
