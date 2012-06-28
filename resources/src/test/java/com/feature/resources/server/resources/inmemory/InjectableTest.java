package com.feature.resources.server.resources.inmemory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * User: ZouYanjian
 * Date: 12-6-26
 * Time: 下午6:12
 * FileName:InjectableTest
 */
public abstract class InjectableTest {
    private static Injector injector;

    public static Injector getInjector() {
        return injector;
    }

    public InjectableTest(Module... modules) {
        injector = Guice.createInjector(modules);
        injector.injectMembers(this);
    }
}
