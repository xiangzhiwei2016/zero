package com.shiro;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.realm.Realm;
import org.springframework.context.ApplicationContext;

@Named
@Singleton
public class RealmFactory {
	private static final Log logger = LogFactory.getLog(RealmFactory.class);

    /**
     * spring context
     */
    @Inject
    private ApplicationContext context;
    
    public Realm getRealm() {
        //根据配置文件拿到对应的realm
        try {
            return (Realm) context.getBean("frameworkRealm");
        } catch (Exception e) {
            logger.error("shiro realm load error, use the default realm.");
        }
        return context.getBean(DefaultRealm.class);
    }
}
