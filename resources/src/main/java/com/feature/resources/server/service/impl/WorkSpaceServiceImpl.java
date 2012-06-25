package com.feature.resources.server.service.impl;

import com.feature.resources.server.dao.WorkSpaceDao;
import com.feature.resources.server.domain.WorkSpace;
import com.feature.resources.server.dto.WorkSpaceDTO;
import com.feature.resources.server.service.WorkSpaceService;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.bson.types.ObjectId;

import javax.annotation.Nullable;
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
        List<WorkSpace> workSpaces =  workSpaceDao.getAllWorkSpace();
        List<WorkSpaceDTO> workSpaceDTOList = Lists.transform(workSpaces,new Function<WorkSpace, WorkSpaceDTO>() {
            @Override
            public WorkSpaceDTO apply(@Nullable WorkSpace input) {
                WorkSpaceDTO workSpaceDTO = new WorkSpaceDTO();
                workSpaceDTO.setId(input.getIdString());
                workSpaceDTO.setName(input.getName());
                return workSpaceDTO;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });
        return workSpaceDTOList;
    }

    @Override
    public WorkSpace getWorkSpaceById(String id) {
        WorkSpace workSpace =  workSpaceDao.findOne("id",new ObjectId(id));
        return workSpace;
    }

}
