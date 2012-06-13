package com.feature.resources.server.resources.context.resolver;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 * User: ZouYanjian
 * Date: 12-6-13
 * Time: 下午1:04
 * FileName:JacksonContextResolver
 */
@Provider
@Produces({MediaType.APPLICATION_JSON})
public class JacksonContextResolver implements ContextResolver<ObjectMapper> {
    private ObjectMapper objectMapper;

    public JacksonContextResolver(){
       this.objectMapper = new ObjectMapper()
                                .configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false)
                                ;
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }
    @Override
    public ObjectMapper getContext(Class<?> type) {
        return objectMapper;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
