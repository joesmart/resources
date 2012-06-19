package com.feature.resources.server.resources.guice;

import com.feature.resources.server.service.TagService;
import com.feature.resources.server.service.impl.TagServiceImpl;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.mockito.Mockito;

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
        tagService = Mockito.mock(TagServiceImpl.class);
        Mockito.when(tagService.exists("test")).thenReturn(false);
        Mockito.when(tagService.exists("xxx")).thenReturn(true);
    }

    @Override
    public TagService get() {
        return tagService;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
