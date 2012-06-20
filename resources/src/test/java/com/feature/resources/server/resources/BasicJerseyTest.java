package com.feature.resources.server.resources;

import com.feature.resources.server.resources.guice.MockGuiceContextListener;
import com.google.inject.servlet.GuiceFilter;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import com.sun.jersey.test.framework.spi.container.TestContainerException;
import com.sun.jersey.test.framework.spi.container.TestContainerFactory;
import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: ZouYanjian
 * Date: 12-6-13
 * Time: 上午10:53
 * FileName:BasicJerseyTest
 */
public class BasicJerseyTest extends JerseyTest {
    public static final Logger LOGGER = LoggerFactory.getLogger(GraphicResourceTest.class);
    protected WebResource resource;

    private static final AppDescriptor APP_DESCRIPTOR = new WebAppDescriptor.Builder("com.feature.resources.server.resources")
            .contextListenerClass(MockGuiceContextListener.class)
            .filterClass(GuiceFilter.class)
//               .contextPath("abc")
            .servletPath("/rs")
            .build();

    public BasicJerseyTest(){
        super(APP_DESCRIPTOR);
    }

    @Override
    protected TestContainerFactory getTestContainerFactory() throws TestContainerException {
        return new OnePerAppDescriptorTestContainerFactory(super.getTestContainerFactory());
    }

    @Before
    public  void setup() {
        resource = resource();
    }


    @After
    public void tearDown() throws Exception{
    }

}
