package com.feature.resources.server.dao;

import com.feature.resources.server.domain.Graphic;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.bson.types.ObjectId;

import java.util.List;

public class GraphicDao extends AppBasicDao<Graphic, ObjectId> {

    private Query<Graphic> graphicQuery;

    @Inject
    protected GraphicDao(Datastore ds) {
        super(ds);
        graphicQuery = createQuery();
    }

    public List<Graphic> findAllByCreateAtTime(){
        List<Graphic> graphics = null;
        graphicQuery.order("-updateDate");
        graphics = Lists.newArrayList(graphicQuery.iterator());
        return graphics;
    }

    public List<Graphic> findByPage(int requestpage, int pageSize) {
        List<Graphic> graphics = null;
        if(requestpage <1){
            requestpage = 1;
        }
        int offset = (requestpage-1) * pageSize;
        graphics = Lists.newArrayList(graphicQuery.offset(offset).limit(pageSize).asList());
        return graphics;
    }
}
