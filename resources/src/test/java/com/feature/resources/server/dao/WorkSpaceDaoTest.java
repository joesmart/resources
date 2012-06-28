package com.feature.resources.server.dao;

import com.feature.resources.server.domain.WorkSpace;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * User: ZouYanjian
 * Date: 12-6-19
 * Time: 下午3:34
 * FileName:WorkSpaceDaoTest
 */
public class WorkSpaceDaoTest extends BasicMongoUnitTest {


    @Before
    public void setUp() throws IOException {

        String[] strings = {"WorkSpace"};
        for (String string : strings) {
            initData(string);
        }

    }

    @After
    public void tearDown() {
        Query<WorkSpace> query = getDatastore().createQuery(WorkSpace.class);
        getDatastore().delete(query);
    }

    @Test
    public void should_return_true_when_workspace_exists() throws Exception {
        Datastore ds = getDatastore();
        WorkSpaceDao dao = new WorkSpaceDao(ds);
        assertThat(dao.isAlreadyExists("name", "test")).isTrue();
    }

    @Test
    public void should_return_false_when_workspace_not_exists() {
        WorkSpaceDao dao = new WorkSpaceDao(getDatastore());
        assertThat(dao.isAlreadyExists("name", "xxx")).isFalse();
    }

    @Test
    public void should_return_all_entity(){
        List<String> workspaceStringList = getResourceStringList("WorkSpace");

        WorkSpaceDao workSpaceDao = new WorkSpaceDao(getDatastore());
        List<WorkSpace> workSpaceList =workSpaceDao.getEntityList();
        assertThat(workSpaceList).isNotNull();
        assertThat(workSpaceList.size()).isEqualTo(workspaceStringList.size());
    }

}
