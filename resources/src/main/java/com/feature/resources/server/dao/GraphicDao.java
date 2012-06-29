package com.feature.resources.server.dao;

import com.feature.resources.server.domain.CheckStatusDesc;
import com.feature.resources.server.domain.Graphic;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import com.google.code.morphia.query.UpdateResults;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.bson.types.ObjectId;

import javax.annotation.Nullable;
import java.util.List;

public class GraphicDao extends AppBasicDao<Graphic, ObjectId> {

    private Query<Graphic> graphicQuery;

    @Inject
    protected GraphicDao(Datastore ds) {
        super(ds);
        graphicQuery = createQuery();
    }

    public List<Graphic> findAllByCreateAtTime() {
        List<Graphic> graphics;
        graphicQuery.order("-createDate");
        graphics = Lists.newArrayList(graphicQuery.iterator());
        return graphics;
    }

    public List<Graphic> findByPage(int requestpage, int pageSize) {
        List<Graphic> graphics;
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

    public List<Graphic> findByPageAndQueryType(int requestPage, int pageSize, CheckStatusDesc desc) {
        List<Graphic> graphics;
        if (requestPage < 1) {
            requestPage = 1;
        }
        int offset = (requestPage - 1) * pageSize;
        graphicQuery = createQuery();
        if (!desc.equals(CheckStatusDesc.ALL))
            graphicQuery.field("checkStatus").equal(desc.getValue());
        graphics = Lists.newArrayList(graphicQuery.offset(offset).limit(pageSize).fetch());
        return graphics;
    }

    public long getTotalRecordCount() {
        return count();
    }
    //TODO need Guice support Exception AOP
    public int updateCheckStatus(List<String> ids,CheckStatusDesc desc){
        Preconditions.checkNotNull(ids,"Id String can't be null");
        Preconditions.checkNotNull(desc,"CheckStatus can't be null");
        if(ids.size() == 0){
            throw new IllegalStateException("Empty Id string List");
        }
        Query<Graphic> query = createQuery();
        List<ObjectId> objectIdList = Lists.transform(ids,new Function<String, ObjectId>() {
            @Override
            public ObjectId apply(@Nullable String input) {
                if(input == null || "".equals(input)){
                    return  new ObjectId();
                }
                return new ObjectId(input);
            }
        });
        query.field("id").in(objectIdList);
        UpdateOperations<Graphic> graphicUpdateOperations = createUpdateOperations();
        graphicUpdateOperations.set("checkStatus",desc.getValue());
        UpdateResults<Graphic> results =update(query,graphicUpdateOperations);
        int updateRow = results.getUpdatedCount() + results.getInsertedCount();
        LOGGER.info(String.valueOf(updateRow));
        return updateRow;
    }

}
