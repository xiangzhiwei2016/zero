package com.core.config;


import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import com.shiro.RealmFactory;


/**
 * shiro 配置类
 *
 * @author xzw
 */
@Configuration
public class ShiroConfigure {
	
    /**
     * shiro SecurityManager
     */
    @Inject
    @Lazy
    private SecurityManager securityManager = null;
    
    @Inject
    @Lazy
    private SessionDAO sessionDao = null;

    /**
    *
    * realm factory
    */
    @Inject
    RealmFactory realmFactory = null;
    
    /**
     * Shiro SecurityManager
     *
     * @return
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @Bean(name = "securityManager")
    @Singleton
    public SecurityManager getSecurityManager() throws InstantiationException, IllegalAccessException, ClassNotFoundException {

        //根据配置文件拿到对应的realm
        Realm frameworkRealm = realmFactory.getRealm();

        DefaultSecurityManager sm = new DefaultSecurityManager(frameworkRealm);
        SecurityUtils.setSecurityManager(sm);

        DefaultSessionManager ssm = new DefaultSessionManager();        
        ssm.setSessionDAO(sessionDao);
        sm.setSessionManager(ssm);
        sm.setCacheManager(new MemoryConstrainedCacheManager());        
        String configvalue = "local";
        if ("local".equals(configvalue)) {
            //设置session过期时间
            long timeout = Long.valueOf("1800000");
            ssm.setGlobalSessionTimeout(timeout);     
        }
        return sm;
    }
    
    /**
     * session dao
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     */
    @Bean
    @Singleton
    public SessionDAO getSessionDAO() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    	return new EnterpriseCacheSessionDAO(); 
    }

    /**
     * Shiro AOP
     *
     * @return
     */
    @Bean
    @Singleton
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor() {

        AuthorizationAttributeSourceAdvisor aasd = new AuthorizationAttributeSourceAdvisor();
        aasd.setSecurityManager(securityManager);

        return aasd;
    }

}
