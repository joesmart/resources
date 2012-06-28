package com.feature.resources.server.resources.rules;

import com.feature.resources.server.resources.GraphicResource;
import com.feature.resources.server.resources.TestResource;
import com.feature.resources.server.resources.inmemory.InjectableTest;
import com.feature.resources.server.resources.inmemory.RestContainerFactory;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.core.DefaultResourceConfig;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.LowLevelAppDescriptor;
import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: ZouYanjian
 * Date: 12-6-26
 * Time: 下午9:01
 * FileName:RestClient
 */
public class RestClient extends ExternalResource {
    public static final Logger LOGGER = LoggerFactory.getLogger(RestClient.class);

    private JerseyTest jerseyRes = new JerseyTest() {
        @Override
        protected AppDescriptor configure() {
            setTestContainerFactory(new RestContainerFactory(InjectableTest.getInjector()));
            DefaultResourceConfig dc = new DefaultResourceConfig();
//            dc.getSingletons().add(TestResource.class);
            dc.getClasses().add(org.codehaus.jackson.jaxrs.JacksonJsonProvider.class);
            dc.getClasses().add(TestResource.class);
            dc.getClasses().add(GraphicResource.class);
            return  new LowLevelAppDescriptor.Builder(dc)
                    .contextPath("rs")
                    .build();
        }
    };

    @Override
    protected void before() throws Throwable {
        super.before();
        jerseyRes.setUp();
    }

    @Override
    protected void after() {
        super.after();
        try {
            jerseyRes.tearDown();
        } catch (Exception e) {
            LOGGER.info("Jersey Test framework error",e);
        }
    }

    public WebResource resource(){
        return  jerseyRes.resource();
    }
}
