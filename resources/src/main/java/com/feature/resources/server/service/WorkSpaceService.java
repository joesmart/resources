package com.feature.resources.server.service;

import com.feature.resources.server.dto.WorkSpaceDTO;

import java.util.List;

/**
 * User: ZouYanjian
 * Date: 12-6-18
 * Time: 下午4:22
 * FileName:WorkSpaceService
 */
public interface WorkSpaceService {
    public void addNewWorkspace(WorkSpaceDTO workspaceDTO);

    public boolean exists(String name);

    public List<WorkSpaceDTO> getCurrentWorkSpaceList();
}
