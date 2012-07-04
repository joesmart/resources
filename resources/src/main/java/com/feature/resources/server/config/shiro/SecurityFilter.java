package com.feature.resources.server.config.shiro;

import com.google.inject.Provider;
import org.apache.shiro.config.Ini;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.web.config.WebIniSecurityManagerFactory;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.ShiroFilter;

import java.util.Map;

/**
 * User: ZouYanjian
 * Date: 12-7-2
 * Time: 下午5:28
 * FileName:SecurityFilter
 */
public class SecurityFilter extends ShiroFilter {
    static  class SecurityManagerFactory extends WebIniSecurityManagerFactory{
        private final WebSecurityManager securityManager;

        public SecurityManagerFactory(WebSecurityManager securityManager){
            this.securityManager = securityManager;
        }

        public SecurityManagerFactory(WebSecurityManager securityManager,Ini ini){
            super(ini);
            this.securityManager = securityManager;
        }

        @Override
        protected SecurityManager createDefaultInstance() {
            return securityManager;
        }
    }

    private final Provider<WebSecurityManager> securityManagerProvider;

    SecurityFilter(Provider<WebSecurityManager> securityManagerProvider){
        super();
        this.securityManagerProvider =securityManagerProvider;
    }
    protected Map<String, ?> applySecurityManager(Ini ini) {
        SecurityManagerFactory factory;
        if (ini == null || ini.isEmpty()) {
            factory = new SecurityManagerFactory(securityManagerProvider.get());
        } else {
            factory = new SecurityManagerFactory(securityManagerProvider.get(), ini);
        }
        setSecurityManager((WebSecurityManager) factory.getInstance());
        return factory.getBeans();
    }
}
