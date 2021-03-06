package com.feature.resources.server.service.impl;

import com.feature.resources.server.dao.WorkSpaceDao;
import com.feature.resources.server.domain.WorkSpace;
import com.feature.resources.server.dto.WorkSpaceDTO;
import com.feature.resources.server.service.WorkSpaceService;
import com.feature.resources.server.util.SystemFunctions;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.bson.types.ObjectId;

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
    @Inject
    private SystemFunctions systemFunctions;

    public void addNewWorkspace(WorkSpaceDTO workspaceDTO) {
        WorkSpace workSpace = new WorkSpace();
        workSpace.setName(workspaceDTO.getName());
        workSpace.setUserId(workspaceDTO.getUserId());
        workSpaceDao.save(workSpace);
    }

    public boolean exists(String name, String userId) {
        return  workSpaceDao.isAlreadyExists("name,userId", name,userId);
    }

    @Override
    public List<WorkSpaceDTO> getCurrentWorkSpaceList() {
        List<WorkSpace> workSpaces =  workSpaceDao.getEntityList();
        List<WorkSpaceDTO> workSpaceDTOList = Lists.transform(workSpaces, systemFunctions.convertWorkSpaceToWorkSpaceDTO());
        return workSpaceDTOList;
    }

    @Override
    public List<WorkSpaceDTO> getCurrentWorkSpaceListByUserId(String userId) {
        List<WorkSpace> workSpaces =  workSpaceDao.getEntityListByUserId(userId);
        List<WorkSpaceDTO> workSpaceDTOList = Lists.transform(workSpaces, systemFunctions.convertWorkSpaceToWorkSpaceDTO());
        return workSpaceDTOList;
    }

    @Override
    public WorkSpace getWorkSpaceById(String id) {
        return workSpaceDao.findOne("id",new ObjectId(id));
    }

    @Override
    public WorkSpace getDefaultWorkSpace(String userId) {
        WorkSpace workSpace = workSpaceDao.defaultWorkSpace(userId);

        if(workSpace == null){
            workSpace = new WorkSpace();
            workSpace.setName("默认");
            workSpace.setUserId(userId);
            workSpaceDao.save(workSpace);
        }
        return workSpace;
    }

}
