package com.feature.resources.server.dao;

import com.feature.resources.server.domain.Permission;
import com.feature.resources.server.domain.Role;
import com.feature.resources.server.domain.TagDescription;
import com.feature.resources.server.domain.User;
import com.google.code.morphia.query.Query;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Nullable;
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
    private List<String> tagDescriptionJsonStrings;

    @Before
    public void setUp() throws IOException {
        String[] strings = {"TagDescription","Permission", "Role", "User"};
        for (String string : strings) {
            initData(string);
        }
        tagDao = new TagDao(getDatastore());
        tagDescriptionJsonStrings = getResourceStringList("TagDescription");
    }

    @After
    public void tearDown(){
        Query<TagDescription> query =  getDatastore().createQuery(TagDescription.class);
        getDatastore().delete(query);
        Query<User> userQuery = getDatastore().createQuery(User.class);
        getDatastore().delete(userQuery);
        Query<Role> roleQuery = getDatastore().createQuery(Role.class);
        getDatastore().delete(roleQuery);
        Query<Permission> permissionQuery = getDatastore().createQuery(Permission.class);
        getDatastore().delete(permissionQuery);
    }

    @Test
    public void should_return_true() throws Exception {
        String value = null;

        if(tagDescriptionJsonStrings.size()>0){
            DBObject object = (DBObject) JSON.parse(tagDescriptionJsonStrings.get(0));
            value = (String) object.get("tag");
        }
        assertThat(tagDao.isAlreadyExists("tag", value)).isTrue();
    }

    @Test
    public void should_return_true_when_user_id_different() throws Exception {
        String value = null;
        String userid = null;

        for(String json:tagDescriptionJsonStrings){
            DBObject object = (DBObject) JSON.parse(json);
            value = (String) object.get("tag");
            userid = (String) object.get("userId");
            assertThat(tagDao.isAlreadyExists("tag,userId", value,userid)).isTrue();
        }
    }



    @Test
    public void should_return_false() throws Exception {
        assertThat(tagDao.isAlreadyExists("tag", "xxxxxx")).isFalse();
    }

    @Test
    public void should_get_a_unqiue_tag_by_id(){
        ObjectId id = null;
        DBObject object = null;
        if(tagDescriptionJsonStrings.size() > 0){
            object = (DBObject) JSON.parse(tagDescriptionJsonStrings.get(0));
            id = (ObjectId) object.get("_id");
        }

        TagDescription tagDescription = tagDao.findOne("id",new ObjectId(id.toString()));
        assertThat(tagDescription).isNotNull();
        assertThat(tagDescription.getTag()).isEqualTo((String) object.get("tag"));
    }

    @Test
    public void should_get_one_size_list_when_get_entityList_by_userId(){
        List<String> resourceList = getResourceStringList("TagDescription");
        String json = resourceList.get(0);
        DBObject dbObject = (DBObject) JSON.parse(json);
        String userId = (String) dbObject.get("userId");

        List<String> userTagList = Lists.newArrayList(Iterators.filter(resourceList.iterator(), new Predicate<String>() {
            @Override
            public boolean apply(@Nullable String input) {
                return input.contains("\"userId\":\"4ff410a897ac21319cf81011\"");
            }
        }));

        List<TagDescription> tagDescriptionList = tagDao.getEntityListByUserId(userId);
        assertThat(tagDescriptionList.size()).isEqualTo(userTagList.size());

    }
}
