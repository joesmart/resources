package com.feature.resources.server.service.impl;

import com.feature.resources.server.dao.WorkSpaceDao;
import com.feature.resources.server.domain.WorkSpace;
import com.feature.resources.server.dto.WorkspaceDTO;
import com.feature.resources.server.service.WorkSpaceService;
import com.google.inject.Inject;

/**
 * User: ZouYanjian
 * Date: 12-6-18
 * Time: 下午4:23
 * FileName:WorkspaceServiceImpl
 */
public class WorkspaceServiceImpl implements WorkSpaceService {
    @Inject
    private WorkSpaceDao workSpaceDao;

    @Override
    public void addNewWorkspace(WorkspaceDTO workspaceDTO) {
        WorkSpace workSpace = new WorkSpace();
        workSpace.setName(workspaceDTO.getName());
        workSpaceDao.save(workSpace);
    }
}
