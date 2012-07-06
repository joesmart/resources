package com.feature.resources.server.config.beanvalidate;

import com.google.inject.Inject;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.hibernate.validator.internal.engine.ValidatorImpl;
import org.hibernate.validator.method.MethodConstraintViolation;
import org.hibernate.validator.method.MethodConstraintViolationException;
import org.hibernate.validator.method.MethodValidator;

import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * User: ZouYanjian
 * Date: 12-7-5
 * Time: 下午1:19
 * FileName:ValidationInterceptor
 */
public class ValidationInterceptor implements MethodInterceptor {

    @Inject
    private ValidatorFactory validatorFactory;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        MethodValidator validator = validatorFactory.getValidator().unwrap(ValidatorImpl.class);
        Set<MethodConstraintViolation<Object>> violations = validator.validateAllParameters(invocation.getThis(), invocation.getMethod(), invocation.getArguments());
        if(!violations.isEmpty()){
            throw new MethodConstraintViolationException(violations);
        }
        Object result = invocation.proceed();
        violations = validator.validateReturnValue(invocation.getThis(),invocation.getMethod(),result);
        if(!violations.isEmpty()){
            throw new MethodConstraintViolationException(violations);
        }
        return result;
    }
}
