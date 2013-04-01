package com.feature.resources.server.service;

import com.feature.resources.server.dao.WorkSpaceDao;
import com.feature.resources.server.domain.WorkSpace;
import com.feature.resources.server.dto.WorkSpaceDTO;
import com.feature.resources.server.service.impl.WorkSpaceServiceImpl;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.Key;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.bson.types.ObjectId;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.jukito.TestSingleton;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

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

    public static class Module extends JukitoModule {

        @Override
        protected void configureTest() {
            forceMock(Datastore.class);
            bindMock(WorkSpaceDao.class).in(TestSingleton.class);
            bind(WorkSpaceService.class).to(WorkSpaceServiceImpl.class).in(TestSingleton.class);
        }
    }

    @Before
    public void setUp(WorkSpaceDao workSpaceDao) throws Exception {
        Mockito.when(workSpaceDao.isAlreadyExists("name", "test")).thenReturn(true);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void should_add_new_workspace_successful(WorkSpaceDao workSpaceDao) {
        WorkSpaceDTO workspaceDTO = new WorkSpaceDTO();
        workspaceDTO.setName("hello");
        workSpaceService.addNewWorkspace(workspaceDTO);
        verify(workSpaceDao).save(Mockito.any(WorkSpace.class));
    }

    @Test
    public void should_return_true(WorkSpaceDao mockDao) {
        when(mockDao.isAlreadyExists("name,userId", "test", "abc")).thenReturn(true);
        boolean result = workSpaceService.exists("test", "abc");
        verify(mockDao).isAlreadyExists("name,userId", "test", "abc");
        assertThat(result).isEqualTo(true);
    }

    @Test
    public void should_return_false(WorkSpaceDao mockDao) {
        when(mockDao.isAlreadyExists("name,userId", "xxxx")).thenReturn(false);
        boolean result = workSpaceService.exists("xxxx", "");
        verify(mockDao).isAlreadyExists("name,userId", "xxxx", "");
        assertThat(result).isEqualTo(false);
    }

    @Test
    public void should_return_workspace_list(WorkSpaceDao mockDao) {
        List<WorkSpace> workSpaces = Lists.newArrayList();
        WorkSpace workSpace = new WorkSpace();
        workSpace.setName("text");
        ObjectId id = new ObjectId();
        workSpace.setId(id);
        workSpaces.add(workSpace);
        when(mockDao.getEntityList()).thenReturn(workSpaces);
        List<WorkSpaceDTO> workSpaceList = workSpaceService.getCurrentWorkSpaceList();
        Assert.assertNotNull(workSpaceList);
        assertThat(workSpaceList.size()).isEqualTo(1);
        assertThat(workSpaceList.get(0).getName()).isEqualTo(workSpace.getName());
        verify(mockDao).getEntityList();
    }

    @Test
    public void should_return_workspace_list_when_query_by_userId(WorkSpaceDao mockDao) {
        String userId = "4ff410a897ac21319cf81011";

        List<WorkSpace> workSpaces = Lists.newArrayList();
        WorkSpace workSpace = new WorkSpace();
        workSpace.setName("text");
        ObjectId id = new ObjectId();
        workSpace.setId(id);
        workSpace.setUserId(userId);
        workSpaces.add(workSpace);

        when(mockDao.getEntityListByUserId(userId)).thenReturn(workSpaces);
        List<WorkSpaceDTO> workSpaceList = workSpaceService.getCurrentWorkSpaceListByUserId(userId);
        Assert.assertNotNull(workSpaceList);
        assertThat(workSpaceList.size()).isEqualTo(1);
        assertThat(workSpaceList.get(0).getName()).isEqualTo(workSpace.getName());
        verify(mockDao).getEntityListByUserId(userId);
    }

    @Test
    public void should_get_workspace_By_id(WorkSpaceDao mockDao) {
        WorkSpace workSpace = new WorkSpace();
        ObjectId id = new ObjectId();
        workSpace.setId(id);
        workSpace.setName("test");
        when(mockDao.findOne("id", id)).thenReturn(workSpace);

        WorkSpace workSpace1 = workSpaceService.getWorkSpaceById(id.toString());
        assertThat(workSpace1).isNotNull();
        assertThat(workSpace1.getName()).isEqualTo("test");
        verify(mockDao).findOne("id", id);
    }

    @Test
    public void should_get_default_workspace_when_workspace_id_is_null(WorkSpaceDao workSpaceDao) {
        WorkSpace mockwWorkSpace = new WorkSpace();
        mockwWorkSpace.setName("默认");
        mockwWorkSpace.setUserId("abcd");
        when(workSpaceDao.defaultWorkSpace("abcd")).thenReturn(mockwWorkSpace);
        WorkSpace workSpace = workSpaceService.getDefaultWorkSpace("abcd");
        assertThat(workSpace).isNotNull();

    }

    @Test
    public void should_could_create_default_workspace_when_default_workspace_not_exits(WorkSpaceDao workSpaceDao) {

        final ObjectId id = new ObjectId();
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                WorkSpace workSpace = (WorkSpace) invocation.getArguments()[0];
                workSpace.setId(id);
                Key<WorkSpace> key = new Key<WorkSpace>(WorkSpace.class,id);
                return key;
            }
        }).when(workSpaceDao).save(any(WorkSpace.class));
        WorkSpace workSpace = workSpaceService.getDefaultWorkSpace("abcd");
        assertThat(workSpace).isNotNull();
        verify(workSpaceDao).save(any(WorkSpace.class));
        assertThat(workSpace.getIdString()).isEqualTo(id.toString());
    }

}
