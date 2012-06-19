package com.feature.resources.server.dao;

import com.feature.resources.server.domain.TagDescription;
import com.google.code.morphia.query.Query;
import org.fest.assertions.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * User: ZouYanjian
 * Date: 12-6-19
 * Time: 下午4:48
 * FileName:TagDaoTest
 */
public class TagDaoTest extends BasicMongoUnitTest{


    @Before
    public void setUp(){
        TagDescription tagDescription = new TagDescription();
        tagDescription.setTag("test");
        getDatastore().save(tagDescription);
    }
    @After
    public void tearDown(){
        Query<TagDescription> query =  getDatastore().createQuery(TagDescription.class);
        getDatastore().delete(query);
    }

    @Test
    public void should_return_true() throws Exception {
        TagDao tagDao = new TagDao(getDatastore());
        Assertions.assertThat(tagDao.isAlreadyExists("test")).isTrue();
    }
    @Test
    public void should_return_false() throws Exception {
        TagDao tagDao = new TagDao(getDatastore());
        Assertions.assertThat(tagDao.isAlreadyExists("xxx")).isFalse();
    }
}
