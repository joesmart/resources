package com.feature.resources.server.resources;

import com.feature.resources.server.dto.TagDTO;
import com.feature.resources.server.service.TagService;
import com.google.inject.Inject;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/tag")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,MediaType.APPLICATION_FORM_URLENCODED})
public class TagResource {
    @Inject
    private TagService tagService;

    @Path("/add")
    @POST
    public Response addNewWorkspace(TagDTO tagDTO){
        tagService.addNewTag(tagDTO);
        return  Response.ok().build();
    }
}
