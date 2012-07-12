package com.feature.resources.server.config.jersey;

import com.feature.resources.server.resources.context.resolver.JacksonContextResolver;
import com.google.common.collect.Maps;
import com.google.inject.Scopes;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

import java.util.Map;

/**
 * User: ZouYanjian
 * Date: 12-7-5
 * Time: 下午2:00
 * FileName:JerseyGuiceModule
 */
public class JerseyGuiceModule extends JerseyServletModule {
    private final Map<String, String> params = Maps.newHashMap();

    @Override
    protected void configureServlets() {
        params.put("javax.ws.rs.Application", "com.feature.resources.server.config.jersey.MainJerseyApplciation");
        params.put("com.sun.jersey.api.json.POJOMappingFeature","true");
        params.put("com.sun.jersey.config.property.packages","com.feature.resources.server.resources");

        bind(JacksonContextResolver.class).in(Scopes.SINGLETON);
        serve("/rs/*").with(GuiceContainer.class, params);
    }
}
