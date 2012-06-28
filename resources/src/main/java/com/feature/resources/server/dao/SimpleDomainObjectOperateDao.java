package com.feature.resources.server.dao;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.dao.BasicDAO;
import com.google.code.morphia.query.Query;

import java.util.List;

/**
 * User: ZouYanjian
 * Date: 12-6-28
 * Time: 下午2:50
 * FileName:SimpleDomainObjectOperateDao
 */
public class SimpleDomainObjectOperateDao<T,K> extends BasicDAO<T,K> {
    public SimpleDomainObjectOperateDao(Datastore ds) {
        super(ds);
    }

    protected Query<T> createQueryFromJudgePropertyAndValue(String propertyName, String propertyValue) {
        Query<T> existsQuery = createQuery();
        existsQuery.field(propertyName).equal(propertyValue);
        return existsQuery;
    }

    public List<T> getEntityList(){
        List<T> entityList = find().asList();
        return entityList;
    }

    public boolean isAlreadyExists(String propertyName, String propertyValue) {
        Query<T> query = createQueryFromJudgePropertyAndValue(propertyName, propertyValue);
        return  exists(query);
    }
}
