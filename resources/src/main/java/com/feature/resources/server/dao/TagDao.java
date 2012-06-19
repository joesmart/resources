package com.feature.resources.server.dao;

import com.feature.resources.server.domain.TagDescription;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.dao.BasicDAO;
import com.google.code.morphia.query.Query;
import com.google.inject.Inject;
import org.bson.types.ObjectId;

/**
 * User: ZouYanjian
 * Date: 12-6-18
 * Time: 下午3:41
 * FileName:TagDao
 */
public class TagDao extends BasicDAO<TagDescription,ObjectId> {
    @Inject
    protected TagDao(Datastore ds) {
        super(ds);
    }

    public boolean isAlreadyExists(String tag) {
        Query<TagDescription> query = createQuery();
        query.field("tag").equal(tag);
        return  exists(query);
    }
}
