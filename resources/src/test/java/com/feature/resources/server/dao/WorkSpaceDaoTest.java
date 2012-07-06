package com.feature.resources.server.dao;

import com.feature.resources.server.domain.*;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Nullable;
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


    private WorkSpaceDao workSpaceDao;

    @Before
    public void setUp() throws IOException {

        String[] strings = {"WorkSpace","Permission", "Role", "User"};
        for (String string : strings) {
            initData(string);
        }
        workSpaceDao = new WorkSpaceDao(getDatastore());
    }

    @After
    public void tearDown() {
        Query<WorkSpace> query = getDatastore().createQuery(WorkSpace.class);
        getDatastore().delete(query);
        Query<User> userQuery = getDatastore().createQuery(User.class);
        getDatastore().delete(userQuery);
        Query<Role> roleQuery = getDatastore().createQuery(Role.class);
        getDatastore().delete(roleQuery);
        Query<Permission> permissionQuery = getDatastore().createQuery(Permission.class);
        getDatastore().delete(permissionQuery);
    }

    @Test
    public void should_return_true_when_workspace_exists() throws Exception {
        Datastore ds = getDatastore();
        WorkSpaceDao dao = new WorkSpaceDao(ds);
        assertThat(dao.isAlreadyExists("name", "test")).isTrue();
    }

    @Test
    public void should_return_false_when_workspace_not_exists() {

        assertThat(workSpaceDao.isAlreadyExists("name", "xxx")).isFalse();
    }

    @Test
    public void should_return_all_entity(){
        List<String> workspaceStringList = getResourceStringList("WorkSpace");

        List<WorkSpace> workSpaceList =workSpaceDao.getEntityList();
        assertThat(workSpaceList).isNotNull();
        assertThat(workSpaceList.size()).isEqualTo(workspaceStringList.size());
    }

    @Test
    public void should_get_one_size_list_when_get_entityList_by_userId(){
        List<String> resourceList = getResourceStringList("WorkSpace");
        List<String> userTagList = Lists.newArrayList(Iterators.filter(resourceList.iterator(), new Predicate<String>() {
            @Override
            public boolean apply(@Nullable String input) {
                return input.contains("userId");
            }
        }));

        String json = userTagList.get(0);
        DBObject dbObject = (DBObject) JSON.parse(json);
        String userId = (String) dbObject.get("userId");

        assertThat(userTagList.size()).isEqualTo(1);
        List<WorkSpace> workSpaceList = workSpaceDao.getEntityListByUserId(userId);
        assertThat(workSpaceList.size()).isEqualTo(1);

    }
}
