package com.feature.resources.server.resources.inmemory;

import com.feature.resources.server.resources.context.resolver.JacksonContextResolver;
import com.google.common.collect.Maps;
import com.google.inject.Scopes;
import com.google.inject.name.Names;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

import java.util.Map;

/**
 * User: ZouYanjian
 * Date: 12-6-26
 * Time: 下午9:54
 * FileName:RestModule
 */
public class RestModule extends JerseyServletModule {
    private final Map<String, String> params = Maps.newHashMap();
    public RestModule(){
        params.put("javax.ws.rs.Application", "com.feature.resources.server.config.jersey.MainJerseyApplciation");
        params.put("com.sun.jersey.api.json.POJOMappingFeature", "true");
        params.put("com.sun.jersey.config.property.packages", "com.feature.resources.server.resources");
    }
    @Override
    protected void configureServlets() {
        bind(JacksonContextResolver.class).in(Scopes.SINGLETON);
        bind(String.class).annotatedWith(Names.named("abc")).toInstance("12312");
        serve("/rs/*").with(GuiceContainer.class, params);
    }
}
