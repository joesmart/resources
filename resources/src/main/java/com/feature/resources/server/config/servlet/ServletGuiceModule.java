package com.feature.resources.server.config.servlet;

import com.feature.resources.server.servlet.LoginServerlet;
import com.feature.resources.server.servlet.RegisterServlet;
import com.feature.resources.server.servlet.URIServlet;
import com.google.inject.servlet.ServletModule;

/**
 * User: ZouYanjian
 * Date: 12-7-5
 * Time: 下午1:52
 * FileName:ServletGuiceModule
 */
public class ServletGuiceModule extends ServletModule {
    @Override
    protected void configureServlets() {
        serve("/servlet/menu").with(URIServlet.class);
        serve("/servlet/login").with(LoginServerlet.class);
        serve("/servlet/register").with(RegisterServlet.class);
    }
}
