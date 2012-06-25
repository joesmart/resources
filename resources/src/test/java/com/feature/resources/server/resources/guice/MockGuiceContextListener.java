package com.feature.resources.server.resources.guice;

import com.feature.resources.server.domain.DomainObjectFactory;
import com.feature.resources.server.resources.context.resolver.JacksonContextResolver;
import com.feature.resources.server.service.GraphicService;
import com.feature.resources.server.service.PropertiesService;
import com.feature.resources.server.service.TagService;
import com.feature.resources.server.service.WorkSpaceService;
import com.feature.resources.server.testdata.TestDataObjectFactory;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.*;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import com.google.inject.servlet.GuiceServletContextListener;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

import java.util.List;
import java.util.Map;

/**
 * User: ZouYanjian
 * Date: 12-6-11
 * Time: 下午2:32
 * FileName:MockGuiceContextListener
 */
public class MockGuiceContextListener extends GuiceServletContextListener {
    private final List<Module> modules = Lists.newArrayList();
    private final Map<String, String> params = Maps.newHashMap();

    @Override
    protected Injector getInjector() {
        params.put("javax.ws.rs.Application", "com.feature.resources.server.config.jersey.MainJerseyApplciation");
        params.put("com.sun.jersey.api.json.POJOMappingFeature", "true");
        params.put("com.sun.jersey.config.property.packages", "com.feature.resources.server.resources");

        JerseyServletModule jerseyServletModule = new JerseyServletModule() {
            @Override
            protected void configureServlets() {
                bind(JacksonContextResolver.class).in(Scopes.SINGLETON);
                bind(String.class).annotatedWith(Names.named("abc")).toInstance("12312");
                serve("/rs/*").with(GuiceContainer.class, params);
            }
        };
        AbstractModule module = new AbstractModule() {
            @Override
            protected void configure() {
                //bind(TestDataObjectFactory.class).annotatedWith(Names.named("testData")).toInstance(new TestDataObjectFactory());
                bind(DomainObjectFactory.class).toProvider(MockDoaminObjectFactoryProvider.class).in(Scopes.SINGLETON);
                bind(GraphicService.class).toProvider(MockGraphicServiceProvider.class).in(Scopes.SINGLETON);
                bind(PropertiesService.class).toProvider(MockPropertiesServiceProvider.class).in(Scopes.SINGLETON);
                bind(WorkSpaceService.class).toProvider(MockWorkSpaceServiceProvider.class).in(Scopes.SINGLETON);
                bind(TagService.class).toProvider(MockTagServiceProvider.class).in(Scopes.SINGLETON);
            }

            @Provides @Named("testData") @Singleton
            public TestDataObjectFactory provideTestDataObjectFactory() {
                return new TestDataObjectFactory();
            }
        };

        modules.add(module);
        modules.add(jerseyServletModule);
        Injector injector = Guice.createInjector(modules);
        return injector;
    }

}
