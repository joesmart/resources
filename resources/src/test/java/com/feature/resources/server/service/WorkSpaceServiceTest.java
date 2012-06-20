package com.feature.resources.server.service;

import com.feature.resources.server.dao.WorkSpaceDao;
import com.feature.resources.server.domain.WorkSpace;
import com.feature.resources.server.dto.WorkspaceDTO;
import com.feature.resources.server.service.impl.WorkspaceServiceImpl;
import com.google.code.morphia.Datastore;
import com.google.inject.Inject;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.jukito.TestSingleton;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * User: ZouYanjian
 * Date: 12-6-19
 * Time: 上午9:52
 * FileName:WorkSpaceServiceTest
 */
@RunWith(JukitoRunner.class)
public class WorkSpaceServiceTest {
    @Inject
    WorkSpaceService workSpaceService;

    public static class Module extends JukitoModule{

        @Override
        protected void configureTest() {
            forceMock(Datastore.class);
            bindMock(WorkSpaceDao.class).in(TestSingleton.class);
            bind(WorkSpaceService.class).to(WorkspaceServiceImpl.class).in(TestSingleton.class);
        }
    }

    @Before
    public void setUp(WorkSpaceDao workSpaceDao) throws Exception {
        Mockito.when(workSpaceDao.isAreadyExists("test")).thenReturn(true);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void should_add_new_workspace_successful(WorkSpaceDao workSpaceDao){
        WorkspaceDTO workspaceDTO = new WorkspaceDTO();
        workspaceDTO.setName("hello");
        workSpaceService.addNewWorkspace(workspaceDTO);
        verify(workSpaceDao).save(Mockito.any(WorkSpace.class));
    }

    @Test
    public void should_return_true(WorkSpaceDao mockDao){
        boolean result =workSpaceService.exists("test");
        verify(mockDao).isAreadyExists("test");
        assertThat(result).isEqualTo(true);
    }

    @Test
    public void should_return_false(WorkSpaceDao mockDao){
        when(mockDao.isAreadyExists("xxxx")).thenReturn(false);
        boolean result =workSpaceService.exists("xxxx");
        verify(mockDao).isAreadyExists("xxxx");
        assertThat(result).isEqualTo(false);
    }
}
