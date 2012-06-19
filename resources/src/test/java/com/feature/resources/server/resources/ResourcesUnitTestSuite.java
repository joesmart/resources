package com.feature.resources.server.resources;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * User: ZouYanjian
 * Date: 12-6-14
 * Time: 上午9:58
 * FileName:ResourcesUnitTestSuite
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        FileResourceTest.class,
        GraphicResourceTest.class,
        TagResourceTest.class,
        WorkspaceResourceTest.class
})
public class ResourcesUnitTestSuite {
}
