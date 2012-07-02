package com.feature.resources.server.resources;

import com.feature.resources.server.domain.Graphic;
import com.feature.resources.server.dto.DataListInfo;
import com.feature.resources.server.dto.GraphicCheckDTO;
import com.feature.resources.server.dto.GraphicDTO;
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
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,MediaType.APPLICATION_FORM_URLENCODED})
public class GraphicResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(GraphicResource.class);

    @Inject
    private GraphicService graphicService;

    @GET
    @Produces({"image/png"})
    public StreamingOutput getGraphicbyId(
            @QueryParam(value = "id") final String graphicId,
            @QueryParam(value = "size") String size) {
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
    @Path("/update")
    @Produces({MediaType.APPLICATION_JSON})
    public Response addNewGraphicResource(GraphicDTO graphicDTO) {
        LOGGER.info("Graphic Info" + graphicDTO.toString());
        graphicService.updateGraphic(graphicDTO);
        return Response.ok().build();
    }

    @GET
    @Path("/pageinfo")
    @Produces({MediaType.APPLICATION_JSON})
    public PageInfo getGraphicPageInfo() {
        long totalRecords = graphicService.getGraphicsTotalCount();
        int pageSize = 10;
        int maxPage = (int)(totalRecords/pageSize);
        int totalPages = (int)(((double)totalRecords/pageSize)>maxPage?maxPage+1:maxPage);
        PageInfo pageInfo = new PageInfo(totalPages, pageSize);
        LOGGER.info("Current Graphics Size:"+ totalRecords);
        return pageInfo;
    }

    @GET
    @Path("/page")
    @Produces({MediaType.APPLICATION_JSON})
    public DataListInfo<Graphic> getGraphicsPage(
    											 @QueryParam("requestPage")int requestPage,
                                                 @QueryParam("pageSize") int pageSize,
                                                 @QueryParam("queryType") String queryType
                                                ){
        LOGGER.info("Query Type:"+queryType);
        List<Graphic> graphics = graphicService.findGraphicByPageAndQueryType(requestPage,pageSize,queryType);
        DataListInfo<Graphic> dataListInfo = new DataListInfo<Graphic>();
        dataListInfo.setDataList(graphics);
        dataListInfo.setName("Graphics");
        dataListInfo.setDataSize(graphics == null? 0:graphics.size());
        return dataListInfo;
    }

    @POST
    @Path("/delete")
    public Response deteleGraphic(@FormParam("id") String id){
        graphicService.delete(id);
        return Response.ok().build();
    }

    @POST
    @Path("/checks")
    @Produces({MediaType.APPLICATION_JSON})
    public Response checkGraphics(GraphicCheckDTO graphicCheckDTO){
        LOGGER.info("GraphicCheckDTO"+graphicCheckDTO.toString());
        graphicService.checkGraphics(graphicCheckDTO);
        return Response.ok().build();
    }

    @POST
    @Path("/batch_delete")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteGraphics(List<String> idString){
        LOGGER.info("GraphicCheckDTO"+idString.toString());
        graphicService.batchDelete(idString);
        return Response.ok().build();
    }
}
