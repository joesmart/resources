package com.feature.resources.server.resources;

import com.feature.resources.server.dto.WorkSpaceDTO;
import com.feature.resources.server.service.WorkSpaceService;
import com.google.inject.Inject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * User: ZouYanjian
 * Date: 12-6-18
 * Time: 下午4:15
 * FileName:WorkspaceResource
 */


@Path("/workspace")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
public class WorkspaceResource extends Resource {
    @Inject
    private WorkSpaceService workSpaceService;

    @Path("/add")
    @POST
    public Response addNewWorkspace(WorkSpaceDTO workspaceDTO) {
        getCurrentUserFromUserssion();
        String userId = null;
        if (shiroUser != null) {
            userId = shiroUser.getUserId();
            workspaceDTO.setUserId(userId);
        }
        if (!workSpaceService.exists(workspaceDTO.getName(), userId)) {
            workSpaceService.addNewWorkspace(workspaceDTO);
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_MODIFIED).build();
        }
    }

    @Path("/all")
    @GET
    public List<WorkSpaceDTO> getAllWorkSpace() {
        getCurrentUserFromUserssion();
        return workSpaceService.getCurrentWorkSpaceListByUserId(shiroUser.getUserId());
    }

}
