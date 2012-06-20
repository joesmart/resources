package com.feature.resources.server.resources;

import com.google.common.collect.Maps;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.spi.container.TestContainer;
import com.sun.jersey.test.framework.spi.container.TestContainerFactory;

import java.net.URI;
import java.util.concurrent.ConcurrentMap;

/**
 * User: ZouYanjian
 * Date: 12-6-20
 * Time: 下午3:20
 * FileName:OnePerAppDescriptorTestContainerFactory
 */
public class OnePerAppDescriptorTestContainerFactory implements TestContainerFactory {
    private static final ConcurrentMap<AppDescriptor,TestContainer> cache = Maps.newConcurrentMap();
    private final TestContainerFactory testContainerFactory;

    public OnePerAppDescriptorTestContainerFactory(TestContainerFactory testContainerFactory){
       this.testContainerFactory = testContainerFactory;
    }
    @Override
    public <T extends AppDescriptor> Class<T> supports() {
        return testContainerFactory.supports();
    }

    @Override
    public TestContainer create(URI baseUri, AppDescriptor ad) throws IllegalArgumentException {
        if(cache.get(ad) == null){
            cache.put(ad,testContainerFactory.create(baseUri,ad));
        }
        return cache.get(ad);
    }
}
