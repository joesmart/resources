package com.feature.resources.server.service;

import com.feature.resources.server.domain.WorkSpace;
import com.feature.resources.server.dto.WorkSpaceDTO;

import java.util.List;

/**
 * User: ZouYanjian
 * Date: 12-6-18
 * Time: 下午4:22
 * FileName:WorkSpaceService
 */
public interface WorkSpaceService {
    void addNewWorkspace(WorkSpaceDTO workspaceDTO);

    boolean exists(String name);

    List<WorkSpaceDTO> getCurrentWorkSpaceList();

    List<WorkSpaceDTO> getCurrentWorkSpaceListByUserId(String userId);

    WorkSpace getWorkSpaceById(String id);
}
