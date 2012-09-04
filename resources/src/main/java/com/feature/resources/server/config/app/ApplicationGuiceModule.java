package com.feature.resources.server.config.app;

import com.feature.resources.server.config.morphia.MorphiaGuiceModule;
import com.feature.resources.server.service.*;
import com.feature.resources.server.service.impl.*;
import com.feature.resources.server.util.DomainObjectFactory;
import com.feature.resources.server.util.SystemFunctions;
import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import com.google.inject.Scopes;
import com.google.inject.name.Names;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * User: ZouYanjian
 * Date: 12-7-5
 * Time: 下午1:54
 * FileName:ApplicationGuiceModule
 */
public class ApplicationGuiceModule extends AbstractModule {
    @Override
    protected void configure() {
        loadProperties(binder());
        install(new MorphiaGuiceModule());
        bind(GraphicService.class).to(GraphicServiceImpl.class).in(Scopes.SINGLETON);
        bind(WorkSpaceService.class).to(WorkSpaceServiceImpl.class).in(Scopes.SINGLETON);
        bind(PropertiesService.class).to(PropertiesServiceImpl.class).in(Scopes.SINGLETON);
        bind(TagService.class).to(TagServiceImpl.class).in(Scopes.SINGLETON);
        bind(UserService.class).to(UserServiceImpl.class).in(Scopes.SINGLETON);
        bind(DomainObjectFactory.class).in(Scopes.SINGLETON);
        bind(SystemFunctions.class);
        bind(CXServerService.class).to(CXServerServiceImpl.class).in(Scopes.SINGLETON);
    }

    private void loadProperties(Binder binder){
        Properties properties = new Properties();
        InputStream inputStream = this.getClass().getResourceAsStream("/app.properties");
        try {
            properties.load(inputStream);
            Names.bindProperties(binder,properties);
        } catch (IOException e) {
            binder.addError(e);
        }

    }
}
