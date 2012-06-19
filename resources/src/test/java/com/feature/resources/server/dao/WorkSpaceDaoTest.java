package com.feature.resources.server.dao;

import com.feature.resources.server.domain.WorkSpace;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import org.fest.assertions.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * User: ZouYanjian
 * Date: 12-6-19
 * Time: 下午3:34
 * FileName:WorkSpaceDaoTest
 */
public class WorkSpaceDaoTest extends BasicMongoUnitTest {

    @Before
    public void setUp() {
        WorkSpace workSpace = new WorkSpace();
        workSpace.setName("test");
        getDatastore().save(workSpace);
    }

    @After
    public void tearDown() {
        Query<WorkSpace> query = getDatastore().createQuery(WorkSpace.class);
        getDatastore().delete(query);
    }

    @Test
    public void should_return_true() throws Exception {
        Datastore ds = getDatastore();
        WorkSpaceDao dao = new WorkSpaceDao(ds);
        Assertions.assertThat(dao.isAreadyExists("test")).isTrue();
    }

    @Test
    public void should_return_false() {
        WorkSpaceDao dao = new WorkSpaceDao(getDatastore());
        Assertions.assertThat(dao.isAreadyExists("xxx")).isFalse();
    }

}
