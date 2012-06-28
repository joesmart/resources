package com.feature.resources.server.resources.inmemory;

import com.google.inject.Injector;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import com.sun.jersey.spi.container.WebApplication;
import com.sun.jersey.spi.container.WebApplicationFactory;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.LowLevelAppDescriptor;
import com.sun.jersey.test.framework.impl.container.inmemory.TestResourceClientHandler;
import com.sun.jersey.test.framework.spi.container.TestContainer;
import com.sun.jersey.test.framework.spi.container.TestContainerFactory;

import java.net.URI;

/**
 * User: ZouYanjian
 * Date: 12-6-26
 * Time: 下午5:58
 * FileName:RestContainerFactory
 */
public class RestContainerFactory implements TestContainerFactory{
    private final Injector injector;

    public RestContainerFactory(Injector injector){
       this.injector = injector;
    }

    @Override
    public Class<LowLevelAppDescriptor> supports() {
        return LowLevelAppDescriptor.class;
    }

    @Override
    public TestContainer create(URI baseUri, AppDescriptor ad) throws IllegalArgumentException {
        return new GuiceTestContainer(baseUri,(LowLevelAppDescriptor)ad,injector);
    }

    public static class GuiceTestContainer implements TestContainer{

        private final URI baseUri;
        private final ResourceConfig conf;
        private final WebApplication app;
        private final Injector injector;

        public GuiceTestContainer(final URI baseUri,final LowLevelAppDescriptor descriptor,final Injector injector){
            this.baseUri = baseUri;
            this.conf = descriptor.getResourceConfig();
            conf.getFeatures().put(ResourceConfig.FEATURE_TRACE,true);
            conf.getFeatures().put(ResourceConfig.FEATURE_TRACE_PER_REQUEST,true);
            this.app = WebApplicationFactory.createWebApplication();
            this.injector = injector;
        }
        @Override
        public Client getClient() {
            return new Client(new TestResourceClientHandler(baseUri,app));
        }

        @Override
        public URI getBaseUri() {
            return baseUri;
        }

        @Override
        public void start() {
           if(!app.isInitiated()){
               /*app.initiate(conf,new GuiceComponentProviderFactory(conf,injector){
                   @Override
                   public Map<Scope, ComponentScope> createScopeMap() {
                       Map<Scope,ComponentScope> map = super.createScopeMap();
                       map.put(ServletScopes.REQUEST,ComponentScope.PerRequest);
                       return map;
                   }
               });*/
               app.initiate(conf,new GuiceContainer(null).new ServletGuiceComponentProviderFactory(conf,injector) );
           }
        }

        @Override
        public void stop() {
            if(app.isInitiated()){
                app.destroy();
            }
        }
    }
}
