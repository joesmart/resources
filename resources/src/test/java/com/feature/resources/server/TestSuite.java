package com.feature.resources.server;

import com.feature.resources.server.dao.DaoUnitTestSuite;
import com.feature.resources.server.resources.ResourcesUnitTestSuite;
import com.feature.resources.server.service.ServiceUnitTestSuite;
import com.feature.resources.server.util.StringUtilTest;
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
        ServiceUnitTestSuite.class,
        DaoUnitTestSuite.class,
        StringUtilTest.class
})
public class TestSuite {
}
