package com.feature.resources.server.config.beanvalidate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * User: ZouYanjian
 * Date: 12-7-5
 * Time: 下午1:16
 * FileName:AutoValidating
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoValidating {

}
