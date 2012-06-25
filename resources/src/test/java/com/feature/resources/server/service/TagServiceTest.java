package com.feature.resources.server.service;

import com.feature.resources.server.dao.TagDao;
import com.feature.resources.server.domain.TagDescription;
import com.feature.resources.server.dto.TagDTO;
import com.feature.resources.server.service.impl.TagServiceImpl;
import com.google.code.morphia.Datastore;
import com.google.inject.Inject;
import org.bson.types.ObjectId;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.jukito.TestSingleton;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.fest.assertions.Assertions.assertThat;
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
        tagService.addNewTag(tagDTO);
        verify(mockDao).save(any(TagDescription.class));
    }

    @Test
    public void should_return_true(TagDao tagDao) {
        String str = "test";
        when(tagDao.isAlreadyExists(str)).thenReturn(true);
        boolean result = tagService.exists(str);
        assertThat(result).isTrue();
    }

    @Test
    public void should_return_false(TagDao tagDao) {
        String str = "xxx";
        when(tagDao.isAlreadyExists(str)).thenReturn(false);
        boolean result = tagService.exists(str);
        assertThat(result).isFalse();
    }

    @Test
    public void should_get_tagDescription_byId(TagDao tagDao) {
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
}
