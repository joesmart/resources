package com.feature.resources.server.dao;

import com.feature.resources.server.domain.Permission;
import com.google.code.morphia.query.Query;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * User: ZouYanjian
 * Date: 12-7-4
 * Time: 下午4:50
 * FileName:PermissionDaoTest
 */
public class PermissionDaoTest extends BasicMongoUnitTest {

    private PermissionDao permissionDao;

    @Before
    public void setUp() throws IOException {
        initData("Permission");
        permissionDao = new PermissionDao(getDatastore());
    }

    @After
    public void tearDown(){
        Query<Permission> query = getDatastore().createQuery(Permission.class);
        getDatastore().delete(query);
    }

    @Test
    public void should_load_test_data_sucessful(){
        List<String> permissionList = getResourceStringList("Permission");
        long size = permissionDao.count();
        assertThat(size).isEqualTo(permissionList.size());
    }

}
