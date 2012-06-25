package com.feature.resources.server.resources.guice;

import com.feature.resources.server.dto.WorkSpaceDTO;
import com.feature.resources.server.service.WorkSpaceService;
import com.feature.resources.server.service.impl.WorkSpaceServiceImpl;
import com.feature.resources.server.testdata.TestDataObjectFactory;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.bson.types.ObjectId;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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
    private TestDataObjectFactory testDataObjectFactory;

    @Inject
    public MockWorkSpaceServiceProvider(TestDataObjectFactory testDataObjectFactory) {
        ObjectId id = new ObjectId();
        WorkSpaceDTO workSpaceDTO = new WorkSpaceDTO();
        workSpaceDTO.setId(id.toString());
        workSpaceDTO.setName("mock");
        List<WorkSpaceDTO> workSpaceDTOList = Lists.newArrayList();
        workSpaceDTOList.add(workSpaceDTO);
        this.testDataObjectFactory = testDataObjectFactory;
        workSpaceService = Mockito.mock(WorkSpaceServiceImpl.class);
        when(workSpaceService.exists("test")).thenReturn(true);
        when(workSpaceService.exists("xxx")).thenReturn(false);
        when(workSpaceService.getCurrentWorkSpaceList()).thenReturn(workSpaceDTOList);

    }
    @Override
    public WorkSpaceService get() {
        return this.workSpaceService;
    }
}
