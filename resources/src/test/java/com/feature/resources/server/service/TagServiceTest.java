package com.feature.resources.server.service;

import com.feature.resources.server.dao.TagDao;
import com.feature.resources.server.domain.TagDescription;
import com.feature.resources.server.dto.TagDTO;
import com.feature.resources.server.service.impl.TagServiceImpl;
import com.feature.resources.server.testdata.TestDataObjectFactory;
import com.google.code.morphia.Datastore;
import com.google.inject.Inject;
import org.bson.types.ObjectId;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.jukito.TestSingleton;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * User: ZouYanjian
 * Date: 12-6-19
 * Time: 上午10:43
 * FileName:TagServiceTest
 */
@RunWith(JukitoRunner.class)
public class TagServiceTest {

    @Inject
    private TagService tagService;

    @Inject
    private TestDataObjectFactory testDataObjectFactory;

    public static class Module extends JukitoModule {

        @Override
        protected void configureTest() {
            bindMock(TagDao.class).in(TestSingleton.class);
            forceMock(Datastore.class);
            bind(TagService.class).to(TagServiceImpl.class).in(TestSingleton.class);
        }
    }

    @Test
    public void should_add_new_tag_successful(TagDao mockDao) {
        TagDTO tagDTO = new TagDTO();
        tagDTO.setTag("test");
        tagDTO.setId("xxxxxxxxxxxxx");
        tagService.addNewTag(tagDTO);
        verify(mockDao).save(any(TagDescription.class));
    }

    @Test
    public void should_return_true_when_tag_already_exists(TagDao tagDao) {
        String str = "test";
        String userId = "abc";
        when(tagDao.isAlreadyExists("tag,userId", str,userId)).thenReturn(true);
        boolean result = tagService.exists(str, userId);
        assertThat(result).isTrue();
    }

    @Test
    public void should_return_false_when_tag_not_exists(TagDao tagDao) {
        String str = "xxx";
        when(tagDao.isAlreadyExists("tag", str)).thenReturn(false);
        boolean result = tagService.exists(str, null);
        assertThat(result).isFalse();
    }

    @Test
    public void should_get_tagDescription_successful_when_get_byId(TagDao tagDao) {
        TagDescription tagDescription = new TagDescription();
        ObjectId id = new ObjectId();
        tagDescription.setId(id);
        tagDescription.setTag("test");
        when(tagDao.findOne("id", id)).thenReturn(tagDescription);
        TagDescription tag = tagService.getTagDescriptionById(id.toString());
        assertThat(tag).isNotNull();
        assertThat(tag.getTag()).isEqualTo("test");
        verify(tagDao).findOne("id", id);
    }

    @Test
    public void should_get_all_entity_successful(TagDao tagDao){
        List<TagDTO> tagDTOList;
        String tag = "Test";
        List<TagDescription> tagDescriptionList = testDataObjectFactory.createTagDescriptionList();
        when(tagDao.getEntityList()).thenReturn(tagDescriptionList);
        tagDTOList = tagService.getCurrentTagList();
        assertThat(tagDTOList).isNotNull();
        assertThat(tagDTOList.size()>0).isTrue();
        assertThat(tagDTOList.size()).isEqualTo(tagDescriptionList.size());
        for(int i=0;i<tagDTOList.size();i++){
            tagDTOList.get(i);
            assertThat(tagDTOList.get(i).getId()).isEqualTo(tagDescriptionList.get(i).getIdString());
            assertThat(tagDTOList.get(i).getTag()).isEqualTo(tagDescriptionList.get(i).getTag());
        }
    }

    @Test
    public void should_get_all_entity_successful_when_query_by_userId(TagDao tagDao){
        List<TagDTO> tagDTOList;
        String tag = "Test";
        List<TagDescription> tagDescriptionList = testDataObjectFactory.createTagDescriptionList();
        String userId = "4ff410a897ac21319cf81011";
        when(tagDao.getEntityListByUserId(userId)).thenReturn(tagDescriptionList);
        tagDTOList = tagService.getCurrentTagListByUserId(userId);
        assertThat(tagDTOList).isNotNull();
        assertThat(tagDTOList.size()>0).isTrue();
        assertThat(tagDTOList.size()).isEqualTo(tagDescriptionList.size());
        for(int i=0;i<tagDTOList.size();i++){
            tagDTOList.get(i);
            assertThat(tagDTOList.get(i).getId()).isEqualTo(tagDescriptionList.get(i).getIdString());
            assertThat(tagDTOList.get(i).getTag()).isEqualTo(tagDescriptionList.get(i).getTag());
        }
    }

}
