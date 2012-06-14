package com.feature.resources.server.resources;

import com.feature.resources.server.domain.Graphic;
import com.feature.resources.server.dto.DataListInfo;
import com.feature.resources.server.dto.PageInfo;
import com.feature.resources.server.service.GraphicService;
import com.feature.resources.server.util.StringUtil;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Path("/graphics")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class GraphicResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(GraphicResource.class);

    @Inject
    GraphicService graphicService;

    @GET
    @Produces({"image/png"})
    public StreamingOutput getGraphicbyId(
            @QueryParam(value = "id") final String graphicId,
            @QueryParam(value = "size") String size) {
        LOGGER.info(graphicId);
        Preconditions.checkNotNull(graphicId);
        if (!Strings.isNullOrEmpty(size)) {
            List<String> sizeList = Lists.newArrayList(Splitter.on('X').split(size));
            final List<Integer> intSizeList = StringUtil.stringSizeListConvertToIntSizeList(sizeList);
            return new StreamingOutput() {
                @Override
                public void write(OutputStream outputStream) throws IOException, WebApplicationException {
                    graphicService.displayThumbnailImage(graphicId, outputStream, intSizeList);
                }
            };
        } else {
            return new StreamingOutput() {
                @Override
                public void write(OutputStream outputStream) throws IOException, WebApplicationException {
                    graphicService.writeOriginalResourceIntoOutputStream(graphicId, outputStream);
                }
            };
        }
    }

    @POST
    @Path("/add")
    @Produces({MediaType.APPLICATION_JSON})
    public Response addNewGraphicResource(String value) {
        LOGGER.info(value);
        return Response.ok("hello").build();
    }

    @GET
    @Path("/pageinfo")
    @Produces({MediaType.APPLICATION_JSON})
    public PageInfo getGraphicPageInfo() {
        PageInfo pageInfo = new PageInfo(10, 10);
        return pageInfo;
    }

    @GET
    @Path("/page")
    @Produces({MediaType.APPLICATION_JSON})
    public DataListInfo<Graphic> getGraphicsPage(@QueryParam("requestPage")int requestPage,
                                                           @QueryParam("pageSize") int pageSize) {
        List<Graphic> graphics = graphicService.findGraphicByPage(requestPage,pageSize);
        DataListInfo<Graphic> dataListInfo = new DataListInfo<Graphic>();
        dataListInfo.setDataList(graphics);
        dataListInfo.setName("Graphics");
        dataListInfo.setDataSize(graphics == null? 0:graphics.size());
        return dataListInfo;
    }

}
