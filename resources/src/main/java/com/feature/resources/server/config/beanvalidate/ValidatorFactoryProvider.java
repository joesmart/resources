package com.feature.resources.server.config.beanvalidate;

import com.google.inject.Provider;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;

/**
 * User: ZouYanjian
 * Date: 12-7-5
 * Time: 下午1:15
 * FileName:ValidatorFactoryProvider
 */
public class ValidatorFactoryProvider implements Provider<ValidatorFactory> {
    @Override
    public ValidatorFactory get() {
        return Validation.buildDefaultValidatorFactory();
    }
}
