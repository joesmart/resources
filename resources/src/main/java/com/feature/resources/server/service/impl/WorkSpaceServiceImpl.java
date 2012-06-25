package com.feature.resources.server.service.impl;

import com.feature.resources.server.dao.WorkSpaceDao;
import com.feature.resources.server.domain.WorkSpace;
import com.feature.resources.server.dto.WorkSpaceDTO;
import com.feature.resources.server.service.WorkSpaceService;
import com.google.inject.Inject;

import java.util.List;

/**
 * User: ZouYanjian
 * Date: 12-6-18
 * Time: 下午4:23
 * FileName:WorkspaceServiceImpl
 */
public class WorkSpaceServiceImpl implements WorkSpaceService {
    @Inject
    private WorkSpaceDao workSpaceDao;

    public void addNewWorkspace(WorkSpaceDTO workspaceDTO) {
        WorkSpace workSpace = new WorkSpace();
        workSpace.setName(workspaceDTO.getName());
        workSpaceDao.save(workSpace);
    }

    public boolean exists(String name) {
        return  workSpaceDao.isAreadyExists(name);
    }

    @Override
    public List<WorkSpaceDTO> getCurrentWorkSpaceList() {
        return null;
    }
}
