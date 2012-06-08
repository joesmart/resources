package com.feature.resources.server.dao;

import com.feature.resources.server.domain.Graphic;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.bson.types.ObjectId;

import java.util.List;

public class GraphicDao extends AppBasicDao<Graphic, ObjectId> {

    @Inject
    protected GraphicDao(Datastore ds) {
        super(ds);
    }

    public List<Graphic> findAllByCreateAtTime(){
        List<Graphic> graphics = null;
        Query<Graphic> query = ds.createQuery(Graphic.class);
        query.order("-updateDate");
        graphics = Lists.newArrayList(query.iterator());
        return graphics;
    }
}
