package com.feature.resources.server.dao;

import com.feature.resources.server.domain.WorkSpace;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.google.inject.Inject;
import org.bson.types.ObjectId;

/**
 * User: ZouYanjian
 * Date: 12-6-18
 * Time: 下午3:39
 * FileName:WorkSpaceDao
 */
public class WorkSpaceDao extends SimpleDomainObjectOperateDao<WorkSpace,ObjectId> {
    @Inject
    protected WorkSpaceDao(Datastore ds) {
        super(ds);
    }

    public WorkSpace defaultWorkSpace(String userId) {
        Query<WorkSpace> query =  createQueryFromJudgePropertyAndValue("name,userId","默认",userId);
        return  query.get();
    }
}
