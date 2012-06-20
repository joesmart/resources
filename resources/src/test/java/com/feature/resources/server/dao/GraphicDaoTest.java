package com.feature.resources.server.dao;

import com.feature.resources.server.domain.Graphic;
import com.google.code.morphia.query.Query;
import com.google.common.base.Preconditions;
import com.google.common.io.Resources;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * User: ZouYanjian
 * Date: 12-6-19
 * Time: 下午5:12
 * FileName:GraphicDaoTest
 */
public class GraphicDaoTest extends BasicMongoUnitTest {
    private GraphicDao graphicDao;

    @Before
    public void setUp() throws IOException {
        String[] strings = {"WorkSpace", "TagDescription", "Properties", "Graphic"};
        for (String string : strings) {
            initData(string);
        }
        graphicDao = new GraphicDao(getDatastore());
    }

    public void initData(String collectionName) throws IOException {
        List<String> collectionLists = getResourceStringList(collectionName);
        DBCollection workSpacesColloection = getDatastore().getDB().getCollection(collectionName);
        for (String json : collectionLists) {
            System.out.println(json);
            DBObject dbObject = (DBObject) JSON.parse(json);
            workSpacesColloection.insert(dbObject);
        }
    }

    public int resourceDataSize(String collectionName) {
        List<String> collections = getResourceStringList(collectionName);
        Preconditions.checkNotNull(collections);
        return collections.size();
    }

    public List<String> getResourceStringList(String collectionName) {
        try {
            String resourceURL = "com/feature/resources/server/testdata/" + collectionName + ".json";
            URL workSpaceUrl = Resources.getResource(resourceURL);
            List<String> collectionLists = Resources.readLines(workSpaceUrl, Charset.defaultCharset());
            return  collectionLists;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @After
    public void tearDown() {
        Query<Graphic> query = getDatastore().createQuery(Graphic.class);
        getDatastore().delete(query);
    }

    @Test
    public void testFindAllByCreateAtTime() throws Exception {
        List<Graphic> graphicList = graphicDao.findAllByCreateAtTime();
        assertThat(graphicList).isNotNull();
        assertThat(graphicList.size()).isEqualTo(resourceDataSize("Graphic"));
        Date maxDate = graphicList.get(0).getCreateDate();
        Date secondDate = graphicList.get(1).getCreateDate();
        assertThat(maxDate.after(secondDate)).isTrue();
    }

    @Test
    public void testFindByPage() throws Exception {
        List<Graphic> graphics =  graphicDao.findByPage(1,10);
        assertThat(graphics).isNotNull();
        int graphicSize = resourceDataSize("Graphic");
        assertThat(graphics.size()).isEqualTo(graphicSize>=10?10:graphicSize);
    }

    @Test
    public void testGetTotalRecordCount() throws Exception {
        long  totalRecordCount  = graphicDao.getTotalRecordCount();
        int graphicSize = resourceDataSize("Graphic");
        assertThat(totalRecordCount).isEqualTo(graphicSize);

    }
}
