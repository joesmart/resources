package com.feature.resources.server.dao;

import com.feature.resources.server.domain.Role;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.dao.BasicDAO;
import com.google.inject.Inject;
import org.bson.types.ObjectId;

/**
 * User: ZouYanjian
 * Date: 12-7-4
 * Time: 下午5:18
 * FileName:RoleDao
 */
public class RoleDao extends BasicDAO<Role,ObjectId> {
    @Inject
    public RoleDao(Datastore ds) {
        super(ds);
    }
}
