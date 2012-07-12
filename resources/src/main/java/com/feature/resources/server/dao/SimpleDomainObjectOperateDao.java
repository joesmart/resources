package com.feature.resources.server.dao;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.dao.BasicDAO;
import com.google.code.morphia.query.Query;
import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

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

    protected Query<T> createQueryFromJudgePropertyAndValue(String propertyNames, String... propertyValues) {
        Query<T> existsQuery = createQuery();

        CharMatcher commaCharMatcher = CharMatcher.is(',');
        List<String> propertyNameList = Lists.newArrayList(Splitter.on(commaCharMatcher).split(propertyNames));
        int propertySize = propertyNameList.size();
        int propertyValueLength = propertyValues.length;
        if(propertySize != propertyValueLength){
            throw new IllegalStateException("参数不匹配!");
        }
        for(int i=0 ;i< propertySize ;i++){
            existsQuery.field(propertyNameList.get(i)).equal(propertyValues[i]);
        }
        return existsQuery;
    }

    public List<T> getEntityList(){
        return find().asList();
    }

    public boolean isAlreadyExists(String propertyNames , String... propertyValues) {

        Query<T> query = createQueryFromJudgePropertyAndValue(propertyNames, propertyValues);
        return  exists(query);
    }

    public List<T> getEntityListByUserId(String userId){

        Query<T> query = createQueryFromJudgePropertyAndValue("userId",userId);
        return  find(query).asList();
    }
}
