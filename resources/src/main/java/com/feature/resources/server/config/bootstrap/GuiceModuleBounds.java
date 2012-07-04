package com.feature.resources.server.config.bootstrap;

import com.feature.resources.server.config.morphia.MorphiaGuiceModule;
import com.feature.resources.server.config.shiro.ShiroConfigurationModule;
import com.feature.resources.server.domain.DomainObjectFactory;
import com.feature.resources.server.resources.context.resolver.JacksonContextResolver;
import com.feature.resources.server.service.GraphicService;
import com.feature.resources.server.service.PropertiesService;
import com.feature.resources.server.service.TagService;
import com.feature.resources.server.service.WorkSpaceService;
import com.feature.resources.server.service.impl.GraphicServiceImpl;
import com.feature.resources.server.service.impl.PropertiesServiceImpl;
import com.feature.resources.server.service.impl.TagServiceImpl;
import com.feature.resources.server.service.impl.WorkSpaceServiceImpl;
import com.feature.resources.server.servlet.LoginServerlet;
import com.feature.resources.server.servlet.URIServlet;
import com.feature.resources.server.util.SystemFunctions;
import com.google.code.morphia.logging.MorphiaLoggerFactory;
import com.google.code.morphia.logging.slf4j.SLF4JLogrImplFactory;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.*;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import org.apache.shiro.guice.aop.ShiroAopModule;
import org.apache.shiro.guice.web.ShiroWebModule;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;
import java.util.List;
import java.util.Map;

@WebListener("Guice Listener")
public class GuiceModuleBounds extends GuiceServletContextListener {

    private final Map<String, String> params = Maps.newHashMap();
    private final List<Module> moudles = Lists.newArrayList();

    private ServletContext servletContext;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        servletContext = servletContextEvent.getServletContext();
        super.contextInitialized(servletContextEvent);
    }

    @Override
    protected Injector getInjector() {
        MorphiaLoggerFactory.registerLogger(SLF4JLogrImplFactory.class);

        params.put("javax.ws.rs.Application", "com.feature.resources.server.config.jersey.MainJerseyApplciation");
        params.put("com.sun.jersey.api.json.POJOMappingFeature","true");
        params.put("com.sun.jersey.config.property.packages","com.feature.resources.server.resources");
        JerseyServletModule jerseyServletModule = new JerseyServletModule(){
            @Override
            protected void configureServlets() {
                bind(JacksonContextResolver.class).in(Scopes.SINGLETON);
                serve("/rs/*").with(GuiceContainer.class, params);
            }
        };
        AbstractModule abstractModule = new AbstractModule() {
            @Override
            protected void configure() {
                install(new MorphiaGuiceModule());
                bind(GraphicService.class).to(GraphicServiceImpl.class).in(Scopes.SINGLETON);
                bind(WorkSpaceService.class).to(WorkSpaceServiceImpl.class).in(Scopes.SINGLETON);
                bind(PropertiesService.class).to(PropertiesServiceImpl.class).in(Scopes.SINGLETON);
                bind(TagService.class).to(TagServiceImpl.class).in(Scopes.SINGLETON);
                bind(DomainObjectFactory.class).in(Scopes.SINGLETON);
                bind(SystemFunctions.class);
            }
        };

        AbstractModule servletModule = new ServletModule() {
            @Override
            protected void configureServlets() {
                serve("/servlet/menu").with(URIServlet.class);
            }
        };
        ServletModule shiroServletModule = new ServletModule(){

            @Override
            protected void configureServlets() {
                super.configureServlets();
                serve("/servlet/login").with(LoginServerlet.class);
            }
        };
        ShiroConfigurationModule shiroConfigurationModule = new ShiroConfigurationModule(servletContext);
        moudles.add(shiroConfigurationModule);
        moudles.add(new ShiroAopModule());
        moudles.add(ShiroWebModule.guiceFilterModule());
        moudles.add(jerseyServletModule);
        moudles.add(abstractModule);
        moudles.add(servletModule);
        moudles.add(shiroServletModule);
        return Guice.createInjector(moudles);
    }
}
