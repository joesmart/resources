package com.feature.resources.server.resources;

import com.feature.resources.server.config.shiro.ShiroBaseRealm;
import com.feature.resources.server.dto.TagDTO;
import com.feature.resources.server.service.TagService;
import com.google.inject.Inject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/tag")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
public class TagResource {
    public static final Logger LOGGER = LoggerFactory.getLogger(TagResource.class);
    @Inject
    private TagService tagService;
    private ShiroBaseRealm.ShiroUser shiroUser;


    @Path("/add")
    @POST
    public Response addNewWorkspace(TagDTO tagDTO) {
        getCurrentUserFromUserssion();
        if(shiroUser != null)
            tagDTO.setUserId(shiroUser.getUserId());
        if (!tagService.exists(tagDTO.getTag())) {
            tagService.addNewTag(tagDTO);
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_MODIFIED).build();
        }
    }

    private void getCurrentUserFromUserssion() {
        Subject currentUser = SecurityUtils.getSubject();
        if(currentUser != null){
            shiroUser = (ShiroBaseRealm.ShiroUser) currentUser.getPrincipal();
            LOGGER.info(shiroUser.toString());
        }
    }

    @Path("/all")
    @GET
    public List<TagDTO> getAllTags(){
        getCurrentUserFromUserssion();
        return tagService.getCurrentTagListByUserId(shiroUser.getUserId());
    }
}
