package com.feature.resources.server.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * User: ZouYanjian
 * Date: 12-6-19
 * Time: 下午4:53
 * FileName:DaoUnitTestSuite
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        TagDaoTest.class,
        WorkSpaceDaoTest.class,
        GraphicDaoTest.class,
        PermissionDaoTest.class,
        UserDaoTest.class
    })
public class DaoUnitTestSuite {

}
