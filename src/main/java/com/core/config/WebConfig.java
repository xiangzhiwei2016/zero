package com.core.config;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.interceptor.SessionInterceptor;

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter{
	/**
	 * 会话过滤
	 */
	@Inject
	private SessionInterceptor sessionInterceptor = null;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(sessionInterceptor).addPathPatterns("/**");
        super.addInterceptors(registry);
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations(
				"/WEB-INF/resources/");

		registry.addResourceHandler("/page/**").addResourceLocations(
				"/WEB-INF/views/",
				"file://"+ "");
	}
	
	public void addViewControllers(ViewControllerRegistry registry) {

		// registry.addRedirectViewController(urlPath, redirectUrl)

	}

	@Bean(name = "multipartResolver")
	@Singleton
	public MultipartResolver getMultipartResolver() {
		return new CommonsMultipartResolver();
	}
}
