package com.feature.resources.server.config.bootstrap;

import com.feature.resources.server.config.app.ApplicationGuiceModule;
import com.feature.resources.server.config.beanvalidate.ValidatorGuiceModule;
import com.feature.resources.server.config.jersey.JerseyGuiceModule;
import com.feature.resources.server.config.servlet.ServletGuiceModule;
import com.feature.resources.server.config.shiro.ShiroConfigurationModule;
import com.google.code.morphia.logging.MorphiaLoggerFactory;
import com.google.code.morphia.logging.slf4j.SLF4JLogrImplFactory;
import com.google.common.collect.Lists;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.servlet.GuiceServletContextListener;
import org.apache.shiro.guice.aop.ShiroAopModule;
import org.apache.shiro.guice.web.ShiroWebModule;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;
import java.util.List;

@WebListener("Guice Listener")
public class GuiceModuleBounds extends GuiceServletContextListener {

//    private final Map<String, String> params = Maps.newHashMap();
    private final List<Module> modules = Lists.newArrayList();

    private ServletContext servletContext;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        servletContext = servletContextEvent.getServletContext();
        super.contextInitialized(servletContextEvent);
    }

    @Override
    protected Injector getInjector() {
        MorphiaLoggerFactory.registerLogger(SLF4JLogrImplFactory.class);

      /*  params.put("javax.ws.rs.Application", "com.feature.resources.server.config.jersey.MainJerseyApplciation");
        params.put("com.sun.jersey.api.json.POJOMappingFeature","true");
        params.put("com.sun.jersey.config.property.packages","com.feature.resources.server.resources");
        JerseyServletModule jerseyServletModule = new JerseyServletModule(){
            @Override
            protected void configureServlets() {
                bind(JacksonContextResolver.class).in(Scopes.SINGLETON);
                serve("/rs*//*").with(GuiceContainer.class, params);
            }
        };*/

        JerseyGuiceModule jerseyGuiceModule = new JerseyGuiceModule();
        ApplicationGuiceModule applicationGuiceModule = new ApplicationGuiceModule();
        ServletGuiceModule servletModule = new ServletGuiceModule();
        ValidatorGuiceModule interceptorModule = new ValidatorGuiceModule();
        ShiroConfigurationModule shiroConfigurationModule = new ShiroConfigurationModule(servletContext);

        modules.add(shiroConfigurationModule);
        modules.add(new ShiroAopModule());
        modules.add(ShiroWebModule.guiceFilterModule());
        modules.add(jerseyGuiceModule);
        modules.add(applicationGuiceModule);
        modules.add(servletModule);
        modules.add(interceptorModule);
        return Guice.createInjector(modules);
    }
}
