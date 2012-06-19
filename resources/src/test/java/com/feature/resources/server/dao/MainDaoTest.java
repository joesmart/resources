package com.feature.resources.server.dao;

import com.feature.resources.server.domain.WorkSpace;
import com.google.code.morphia.query.Query;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * User: ZouYanjian
 * Date: 12-6-19
 * Time: 下午5:39
 * FileName:MainDaoTest
 */
public class MainDaoTest extends BasicMongoUnitTest {

    @Before
    public void setUp(){
        String json = "{ \"_id\" : { \"$oid\" : \"4fdeed8997acc121c3d6d330\" }, \"className\" : \"com.feature.resources.server.domain.WorkSpace\", \"name\" : \"adfadf\", \"type\" : \"Base\", \"updateDate\" : { \"$date\" : 1340009864631 }, \"createDate\" : { \"$date\" : 1340009864630 }, \"version\" : 1340009865343 }";

        DBCollection collection = getDatastore().getDB().getCollection("WorkSpace");
        DBObject dbObject = (DBObject) JSON.parse(json);
        collection.insert(dbObject);
    }

    @After
    public void tearDown(){
        Query<WorkSpace> query = getDatastore().createQuery(WorkSpace.class);
        getDatastore().delete(query);
    }

    @Test
    public void testFindWorkSpace(){
        WorkSpaceDao workSpaceDao = new WorkSpaceDao(getDatastore());
        WorkSpace workSpace = workSpaceDao.findOne("id",new ObjectId("4fdeed8997acc121c3d6d330"));
        assertThat(workSpace).isNotNull();
        assertThat(workSpace.getName()).isEqualTo("adfadf");
    }
}
