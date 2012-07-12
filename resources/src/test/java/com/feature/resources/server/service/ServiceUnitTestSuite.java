package com.feature.resources.server.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * User: ZouYanjian
 * Date: 12-6-19
 * Time: 上午10:59
 * FileName:ServiceUnitTestSuite
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
        GraphicServiceTest.class,
        TagServiceTest.class,
        WorkSpaceServiceTest.class,
        PropertiesServiceTest.class,
        UserServiceTest.class
})
public class ServiceUnitTestSuite {
}
