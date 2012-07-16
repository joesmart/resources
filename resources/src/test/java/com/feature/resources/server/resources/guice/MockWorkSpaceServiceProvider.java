package com.feature.resources.server.resources.guice;

import com.feature.resources.server.domain.WorkSpace;
import com.feature.resources.server.dto.WorkSpaceDTO;
import com.feature.resources.server.service.WorkSpaceService;
import com.feature.resources.server.service.impl.WorkSpaceServiceImpl;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.bson.types.ObjectId;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

/**
 * User: ZouYanjian
 * Date: 12-6-19
 * Time: 上午11:03
 * FileName:MockWorkSpaceServiceProvider
 */
public class MockWorkSpaceServiceProvider implements Provider<WorkSpaceService> {
    public static final Logger LOGGER = LoggerFactory.getLogger(MockWorkSpaceServiceProvider.class);
    private WorkSpaceService workSpaceService;

    @Inject
    public MockWorkSpaceServiceProvider() {
        final ObjectId id = new ObjectId();
        WorkSpaceDTO workSpaceDTO = new WorkSpaceDTO();
        workSpaceDTO.setId(id.toString());
        workSpaceDTO.setName("mock");
        List<WorkSpaceDTO> workSpaceDTOList = Lists.newArrayList();
        workSpaceDTOList.add(workSpaceDTO);


        workSpaceService = Mockito.mock(WorkSpaceServiceImpl.class);
        when(workSpaceService.exists("test", "4ff687ae97acbe55b0b3591c")).thenReturn(true);
        when(workSpaceService.exists("xxx", "4ff687ae97acbe55b0b3591c")).thenReturn(false);
        when(workSpaceService.getCurrentWorkSpaceList()).thenReturn(workSpaceDTOList);
        when(workSpaceService.getCurrentWorkSpaceListByUserId(anyString())).thenReturn(workSpaceDTOList);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String userId = (String) invocation.getArguments()[0];
                WorkSpace workSpace = new WorkSpace();
                workSpace.setId(id);
                workSpace.setName("默认");
                workSpace.setUserId(userId);
                return workSpace;
            }
        }).when(workSpaceService).getDefaultWorkSpace(anyString());

    }
    @Override
    public WorkSpaceService get() {
        return this.workSpaceService;
    }
}
