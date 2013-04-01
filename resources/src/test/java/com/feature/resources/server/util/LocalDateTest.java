package com.feature.resources.server.util;

import org.fest.assertions.api.Assertions;
import org.joda.time.LocalDateTime;
import org.joda.time.Minutes;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: ZouYanjian
 * Date: 8/31/12
 * Time: 2:34 PM
 * FileName:LocalDateTest
 */
public class LocalDateTest {

    public static final Logger LOGGER = LoggerFactory.getLogger(LocalDateTest.class);
    @Test
    public void test(){
        LocalDateTime localDate = LocalDateTime.now();
        LocalDateTime date2 = localDate.minus(Minutes.minutes(15));
        LOGGER.info(localDate.toString());
        LOGGER.info(date2.toString());
        Assertions.assertThat(localDate.toDate().getTime()-date2.toDate().getTime()).isEqualTo(900000L);

    }
}
