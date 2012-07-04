package com.feature.resources.server.config.shiro;

import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import org.apache.shiro.config.Ini;
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
        bindConstant().annotatedWith(Names.named("shiro.successUrl")).to("main.jsp");
        bindConstant().annotatedWith(Names.named("shiro.loginUrl")).to("/login.jsp");
        bindRealm().to(ShiroBaseRealm.class).in(Singleton.class);
      /*  try {
            bindRealm().toConstructor(IniRealm.class.getConstructor(Ini.class));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }*/
//        bind(Realm.class).to(ShiroBaseRealm.class).in(Singleton.class);
//        expose(ShiroBaseRealm.class);
        addFilterChain("/login.jsp", AUTHC);
        addFilterChain("/logout",LOGOUT);
        addFilterChain("/static/**", ANON);
//        addFilterChain("/servlet/menu*//**",AUTHC_BASIC,config(PERMS,"no"));
        addFilterChain("/**", USER);
//        addFilterChain( "/**", AUTHC_BASIC, config( AUTHC_BASIC, "sample:permToCatchAllUnprotecteds" ) );
        processMethodInterceptors();
    }

    private void processMethodInterceptors(){
//        MethodInterceptor interceptor = new AopAllianceAnnotationsAuthorizingMethodInterceptor();
    }

    @Provides
    @Singleton
    Ini shiroIni() {
        return Ini.fromResourcePath("classpath:shiro.ini");
    }

}
