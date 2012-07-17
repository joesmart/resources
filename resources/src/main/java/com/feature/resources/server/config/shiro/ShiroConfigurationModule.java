package com.feature.resources.server.config.shiro;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import org.apache.shiro.guice.web.ShiroWebModule;
import org.apache.shiro.mgt.CachingSecurityManager;
import org.apache.shiro.mgt.DefaultSecurityManager;

import javax.servlet.ServletContext;

/**
 * User: ZouYanjian
 * Date: 12-7-2
 * Time: 下午4:07
 * FileName:ShiroConfigurationModule
 */
public class ShiroConfigurationModule extends ShiroWebModule {
    @Inject
    public ShiroConfigurationModule(ServletContext servletContext){
        super(servletContext);
    }
    @Override
    protected void configureShiroWeb() {
        bind(CachingSecurityManager.class).to(DefaultSecurityManager.class).in(Singleton.class);
        bindConstant().annotatedWith(Names.named("shiro.successUrl")).to("index.jsp");
        bindConstant().annotatedWith(Names.named("shiro.loginUrl")).to("/login.jsp");
        bindRealm().to(ShiroBaseRealm.class).in(Singleton.class);

        addFilterChain("/login.jsp", AUTHC);
        addFilterChain("/logout",LOGOUT);
        addFilterChain("/register.jsp",ANON);
        addFilterChain("/servlet/register",ANON);
        addFilterChain("/static/**", ANON);
        addFilterChain("/rs/file/**/meta",ANON);
        addFilterChain("/rs/graphics/show**",ANON);
        addFilterChain("/rs/**",AUTHC_BASIC,REST,NO_SESSION_CREATION);
        addFilterChain("/**",AUTHC, REST,NO_SESSION_CREATION,USER);

        processMethodInterceptors();
    }

    private void processMethodInterceptors(){
//        MethodInterceptor interceptor = new AopAllianceAnnotationsAuthorizingMethodInterceptor();
    }
}
