package com.feature.resources.server.config.beanvalidate;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.matcher.Matchers;

import javax.validation.ValidatorFactory;

/**
 * User: ZouYanjian
 * Date: 12-7-5
 * Time: 下午1:50
 * FileName:ValidatorGuiceModule
 */
public class ValidatorGuiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ValidatorFactory.class).toProvider(ValidatorFactoryProvider.class).in(Scopes.SINGLETON);
        ValidationInterceptor validationInterceptor = new ValidationInterceptor();
        requestInjection(validationInterceptor);
        bindInterceptor(
                Matchers.annotatedWith(AutoValidating.class),
                Matchers.any(),
                validationInterceptor
        );
    }
}
