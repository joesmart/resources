package com.feature.resources.server.resources;

import com.feature.resources.server.dto.TagDTO;
import com.feature.resources.server.service.TagService;
import com.google.inject.Inject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/tag")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
public class TagResource {
    @Inject
    private TagService tagService;

    @Path("/add")
    @POST
    public Response addNewWorkspace(TagDTO tagDTO) {
        if (!tagService.exists(tagDTO.getTag())) {
            tagService.addNewTag(tagDTO);
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_MODIFIED).build();
        }
    }

    @Path("/all")
    @GET
    public List<TagDTO> getAllTags(){
        return tagService.getCurrentTagList();
    }
}
