package com.feature.resources.server.dao;

import com.feature.resources.server.domain.Permission;
import com.feature.resources.server.domain.Role;
import com.feature.resources.server.domain.User;
import com.google.code.morphia.query.Query;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * User: ZouYanjian
 * Date: 12-7-5
 * Time: 下午3:51
 * FileName:UserDaoTest
 */
public class UserDaoTest extends BasicMongoUnitTest {

    private UserDao userDao;

    @Before
    public void setUp() throws IOException {
        String[] strings = {"Permission", "Role", "User"};
        for (String string : strings) {
            initData(string);
        }
        userDao = new UserDao(getDatastore());
    }

    @After
    public void tearDown() {
        Query<User> userQuery = getDatastore().createQuery(User.class);
        getDatastore().delete(userQuery);
        Query<Role> roleQuery = getDatastore().createQuery(Role.class);
        getDatastore().delete(roleQuery);
        Query<Permission> permissionQuery = getDatastore().createQuery(Permission.class);
        getDatastore().delete(permissionQuery);

    }

    @Test
    public void should_get_null_when_loginName_nonexists() throws Exception {
        User user = userDao.findUserByLoginName("abcdadf123");
        assertThat(user).isNull();
    }

    @Test
    public void should_get_user_when_user_isExists_query_loginName_(){
        List<String> userStringList = getResourceStringList("User");
        for(String json:userStringList){
           DBObject dbObject = (DBObject) JSON.parse(json);
            String loginName = (String) dbObject.get("loginNane");
            if(StringUtils.isNotEmpty(loginName)){
                User user = userDao.findUserByLoginName(loginName);
                assertThat(user).isNotNull();
                assertThat(user.getLoginName()).isEqualTo(loginName);
            }
        }
    }

    @Test
    public void should_get_true_when_user_isExists_queryby_loginName(){
        List<String> userStringList = getResourceStringList("User");
        for(String json:userStringList){
            DBObject dbObject = (DBObject) JSON.parse(json);
            String loginName = (String) dbObject.get("loginNane");
            if(StringUtils.isNotEmpty(loginName)){
                boolean result = userDao.exists("loginName",loginName);
                assertThat(result).isTrue();
            }
        }
    }

}
