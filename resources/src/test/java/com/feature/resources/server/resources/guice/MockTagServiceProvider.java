package com.feature.resources.server.resources.guice;

import com.feature.resources.server.dto.TagDTO;
import com.feature.resources.server.service.TagService;
import com.feature.resources.server.service.impl.TagServiceImpl;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.bson.types.ObjectId;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.Mockito.when;

/**
 * User: ZouYanjian
 * Date: 12-6-19
 * Time: 下午3:08
 * FileName:MockTagServiceProvider
 */
public class MockTagServiceProvider implements Provider<TagService> {
    private TagService tagService;
    @Inject
    public MockTagServiceProvider(){
        TagDTO tagDTO = new TagDTO();
        ObjectId id = new ObjectId();
        tagDTO.setId(id.toString());
        tagDTO.setTag("mock");
        List<TagDTO> tagDTOList = Lists.newArrayList();
        tagDTOList.add(tagDTO);
        tagService = Mockito.mock(TagServiceImpl.class);
        when(tagService.exists("test")).thenReturn(false);
        when(tagService.exists("xxx")).thenReturn(true);
        when(tagService.getCurrentTagList()).thenReturn(tagDTOList);
    }

    @Override
    public TagService get() {
        return tagService;
    }
}
