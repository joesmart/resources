package com.feature.resources.server.dao;

import com.feature.resources.server.domain.Properties;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.dao.BasicDAO;
import com.google.inject.Inject;
import org.bson.types.ObjectId;

/**
 * User: ZouYanjian
 * Date: 12-6-5
 * Time: 上午10:39
 * FileName:PropertiesDao
 */
public class PropertiesDao extends BasicDAO<Properties,ObjectId> {

    @Inject
    protected PropertiesDao(Datastore ds) {
        super(ds);
    }
}
