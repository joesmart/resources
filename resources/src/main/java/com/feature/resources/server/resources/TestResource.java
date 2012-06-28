package com.feature.resources.server.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * User: ZouYanjian
 * Date: 12-6-26
 * Time: 下午9:45
 * FileName:TestResource
 */
@Path("test")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
public class TestResource {
    @GET
    public String sayHello(){
        return "hello";
    }
}
