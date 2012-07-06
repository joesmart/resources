package com.feature.resources.server.resources.inmemory;

import com.feature.resources.server.util.DomainObjectFactory;
import com.feature.resources.server.resources.guice.*;
import com.feature.resources.server.service.GraphicService;
import com.feature.resources.server.service.PropertiesService;
import com.feature.resources.server.service.TagService;
import com.feature.resources.server.service.WorkSpaceService;
import com.feature.resources.server.testdata.TestDataObjectFactory;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

/**
 * User: ZouYanjian
 * Date: 12-6-26
 * Time: 下午9:58
 * FileName:RestTest
 */
public class RestTest extends InjectableTest {
    public RestTest() {
        super(new RestModule(),new AbstractModule() {
            @Override
            protected void configure() {
                //bind(TestDataObjectFactory.class).annotatedWith(Names.named("testData")).toInstance(new TestDataObjectFactory());
                bind(DomainObjectFactory.class).toProvider(MockDoaminObjectFactoryProvider.class).in(Scopes.SINGLETON);
                bind(GraphicService.class).toProvider(MockGraphicServiceProvider.class).in(Scopes.SINGLETON);
                bind(PropertiesService.class).toProvider(MockPropertiesServiceProvider.class).in(Scopes.SINGLETON);
                bind(WorkSpaceService.class).toProvider(MockWorkSpaceServiceProvider.class).in(Scopes.SINGLETON);
                bind(TagService.class).toProvider(MockTagServiceProvider.class).in(Scopes.SINGLETON);
            }

            @Provides
            @Named("testData") @Singleton
            public TestDataObjectFactory provideTestDataObjectFactory() {
                return new TestDataObjectFactory();
            }
        });
    }
}
