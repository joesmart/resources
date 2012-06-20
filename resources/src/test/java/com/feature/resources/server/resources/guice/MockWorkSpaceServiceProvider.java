package com.feature.resources.server.resources.guice;

import com.feature.resources.server.service.WorkSpaceService;
import com.feature.resources.server.service.impl.WorkspaceServiceImpl;
import com.feature.resources.server.testdata.TestDataObjectFactory;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public MockWorkSpaceServiceProvider(TestDataObjectFactory testDataObjectFactory){
        this.testDataObjectFactory = testDataObjectFactory;
        workSpaceService = Mockito.mock(WorkspaceServiceImpl.class);
        Mockito.when(workSpaceService.exists("test")).thenReturn(true);
        Mockito.when(workSpaceService.exists("xxx")).thenReturn(false);

    }
    @Override
    public WorkSpaceService get() {
        return this.workSpaceService;
    }
}
