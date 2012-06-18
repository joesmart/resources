package com.feature.resources.server.dao;

import com.feature.resources.server.domain.WorkSpace;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.dao.BasicDAO;
import com.google.inject.Inject;
import org.bson.types.ObjectId;

/**
 * User: ZouYanjian
 * Date: 12-6-18
 * Time: 下午3:39
 * FileName:WorkSpaceDao
 */
public class WorkSpaceDao extends BasicDAO<WorkSpace,ObjectId> {
    @Inject
    protected WorkSpaceDao(Datastore ds) {
        super(ds);
    }

}
