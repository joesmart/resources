package com.feature.resources.server.dao;

import com.feature.resources.server.domain.TagDescription;
import com.google.code.morphia.query.Query;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * User: ZouYanjian
 * Date: 12-6-19
 * Time: 下午4:48
 * FileName:TagDaoTest
 */
public class TagDaoTest extends BasicMongoUnitTest{

    private TagDao tagDao;
    private List<String> jsonStrings;

    @Before
    public void setUp() throws IOException {
        String[] strings = {"TagDescription"};
        for (String string : strings) {
            initData(string);
        }
        tagDao = new TagDao(getDatastore());
        jsonStrings = getResourceStringList("TagDescription");
    }

    @After
    public void tearDown(){
        Query<TagDescription> query =  getDatastore().createQuery(TagDescription.class);
        getDatastore().delete(query);
    }

    @Test
    public void should_return_true() throws Exception {
        String value = null;

        if(jsonStrings.size()>0){
            DBObject object = (DBObject) JSON.parse(jsonStrings.get(0));
            value = (String) object.get("tag");
        }
        assertThat(tagDao.isAlreadyExists(value)).isTrue();
    }
    @Test
    public void should_return_false() throws Exception {
        assertThat(tagDao.isAlreadyExists("xxxxxx")).isFalse();
    }

    @Test
    public void should_get_a_unqiue_tag_by_id(){
        ObjectId id = null;
        DBObject object = null;
        if(jsonStrings.size() > 0){
            object = (DBObject) JSON.parse(jsonStrings.get(0));
            id = (ObjectId) object.get("_id");
        }

        TagDescription tagDescription = tagDao.findOne("id",new ObjectId(id.toString()));
        assertThat(tagDescription).isNotNull();
        assertThat(tagDescription.getTag()).isEqualTo((String) object.get("tag"));
    }
}
