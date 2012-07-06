package com.feature.resources.server.dao;

import com.feature.resources.server.domain.Permission;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.dao.BasicDAO;
import com.google.inject.Inject;
import org.bson.types.ObjectId;

/**
 * User: ZouYanjian
 * Date: 12-7-4
 * Time: 下午4:32
 * FileName:PermissionDao
 */
public class PermissionDao extends BasicDAO<Permission,ObjectId> {
    @Inject
    public PermissionDao(Datastore ds) {
        super(ds);
    }
}
