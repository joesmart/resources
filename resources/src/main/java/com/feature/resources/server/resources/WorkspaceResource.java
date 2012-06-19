package com.feature.resources.server.resources;

import com.feature.resources.server.dto.WorkspaceDTO;
import com.feature.resources.server.service.WorkSpaceService;
import com.google.inject.Inject;
import lombok.Data;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * User: ZouYanjian
 * Date: 12-6-18
 * Time: 下午4:15
 * FileName:WorkspaceResource
 */


@Path("/workspace")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,MediaType.APPLICATION_FORM_URLENCODED})
@Data
public class WorkspaceResource {
    @Inject
    private WorkSpaceService workSpaceService;

    @Path("/add")
    @POST
    public Response addNewWorkspace(WorkspaceDTO workspaceDTO){
        if(!workSpaceService.exists(workspaceDTO.getName())){
            workSpaceService.addNewWorkspace(workspaceDTO);
            return  Response.ok().build();
        }else{
            return  Response.status(Response.Status.NOT_MODIFIED).build();
        }
    }
}
