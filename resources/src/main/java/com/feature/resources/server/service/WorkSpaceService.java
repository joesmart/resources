package com.feature.resources.server.service;

import com.feature.resources.server.dto.WorkspaceDTO;

/**
 * User: ZouYanjian
 * Date: 12-6-18
 * Time: 下午4:22
 * FileName:WorkSpaceService
 */
public interface WorkSpaceService {
    public void addNewWorkspace(WorkspaceDTO workspaceDTO);

    public boolean exists(String name);
}
