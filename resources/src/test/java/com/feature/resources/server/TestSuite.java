package com.feature.resources.server;

import com.feature.resources.server.resources.ResourcesUnitTestSuite;
import com.feature.resources.server.service.impl.GraphicServiceImplTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * User: ZouYanjian
 * Date: 12-6-14
 * Time: 上午9:56
 * FileName:TestSuite
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ResourcesUnitTestSuite.class,
        GraphicServiceImplTest.class
})
public class TestSuite {
}
