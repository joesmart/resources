package com.feature.resources.server.dao;

import com.feature.resources.server.domain.CheckStatusDesc;
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

    public List<Graphic> findAllByCreateAtTime() {
        List<Graphic> graphics = null;
        graphicQuery.order("-createDate");
        graphics = Lists.newArrayList(graphicQuery.iterator());
        return graphics;
    }

    public List<Graphic> findByPage(int requestpage, int pageSize) {
        List<Graphic> graphics = null;
        if (requestpage < 1) {
            requestpage = 1;
        }
        int offset = (requestpage - 1) * pageSize;
        ds.getMapper().createEntityCache();
        graphicQuery = createQuery();
        graphicQuery.order("-createDate");
        graphics = Lists.newArrayList(graphicQuery.offset(offset).limit(pageSize).fetch());
        return graphics;
    }

    public List<Graphic> findByPageAndQueryType(int requestpage, int pageSize, CheckStatusDesc desc) {
        List<Graphic> graphics = null;
        if (requestpage < 1) {
            requestpage = 1;
        }
        int offset = (requestpage - 1) * pageSize;
        graphicQuery = createQuery();
        if (!desc.equals(CheckStatusDesc.ALL))
            graphicQuery.field("checkStatus").equal(desc.getValue());
        graphics = Lists.newArrayList(graphicQuery.offset(offset).limit(pageSize).fetch());
        return graphics;
    }

    public long getTotalRecordCount() {
        long totalrecord = count();
        return totalrecord;  //To change body of created methods use File | Settings | File Templates.
    }
}
