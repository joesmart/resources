package com.feature.resources.server.dao;

import com.feature.resources.server.domain.User;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.dao.BasicDAO;
import com.google.code.morphia.query.Query;
import com.google.inject.Inject;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * User: ZouYanjian
 * Date: 12-7-4
 * Time: 下午5:35
 * FileName:UserDao
 */
public class UserDao extends BasicDAO<User,ObjectId> {
    @Inject
    public UserDao(Datastore ds) {
        super(ds);
    }

    public User findUserByLoginName(String loginName) {
        Query<User> query = createQuery();
        query.field("loginName").equal(loginName);
        List<User> userList =  query.asList();
        if(userList != null && userList.size() == 1){
            return userList.get(0);
        }
        return null;
    }
}
