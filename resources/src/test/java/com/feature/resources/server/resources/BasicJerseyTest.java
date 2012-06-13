package com.feature.resources.server.resources;

import com.feature.resources.server.resources.guice.MockGuiceContextListener;
import com.google.inject.servlet.GuiceFilter;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;

/**
 * User: ZouYanjian
 * Date: 12-6-13
 * Time: 上午10:53
 * FileName:BasicJerseyTest
 */
public class BasicJerseyTest extends JerseyTest {
    public BasicJerseyTest(){
        super(new WebAppDescriptor.Builder("com.feature.resources.server.resources")
                .contextListenerClass(MockGuiceContextListener.class)
                .filterClass(GuiceFilter.class)
//                .contextPath("")
                .servletPath("rs")
                .build());

    }
}
