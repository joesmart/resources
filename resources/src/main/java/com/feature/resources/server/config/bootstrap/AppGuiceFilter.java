package com.feature.resources.server.config.bootstrap;

import com.google.inject.servlet.GuiceFilter;

import javax.servlet.annotation.WebFilter;

@WebFilter(urlPatterns = "/*",filterName = "AppGuiceFilter",displayName = "Guice Filter")
public class AppGuiceFilter extends GuiceFilter {

}
