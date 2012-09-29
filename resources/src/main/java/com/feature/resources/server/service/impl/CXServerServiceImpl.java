package com.feature.resources.server.service.impl;

import com.feature.resources.server.dto.GraphicCheckDTO;
import com.feature.resources.server.service.CXServerService;
import com.feature.resources.server.util.JerseyResourceUtil;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;

/**
 * User: ZouYanjian
 * Date: 8/30/12
 * Time: 2:23 PM
 * FileName:CXServerServiceImpl
 */
public class CXServerServiceImpl implements CXServerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CXServerServiceImpl.class);
    @Inject
    @Named("cxserver.url")
    private String cxServerHost;

    @Inject
    @Named("cxserver.name")
    private String cxServerName;

    @Inject
    @Named("cxserver.port")
    private String cxServerPort;

    @Inject
    @Named("cxserver.audit.service")
    private String cxServerAuditService;

    @Inject
    @Named("cxserver.audit.method")
    private String method;

    @Inject
    @Named("audit.close")
    private boolean isAuditClose;

    @Override
    public void updateGraphicResourceAuditStatus(GraphicCheckDTO graphicCheckDTO) {
        if (isAuditClose)
            return;
        WebResource webResource = JerseyResourceUtil.getClient().resource(createURL());
        ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, graphicCheckDTO);
        if (clientResponse.getStatus()>210) {
            throw new RuntimeException("error:"+clientResponse.getStatus()+"");
        }
    }

    private String createURL() {
        String url = "http://" + cxServerHost + ":" + cxServerPort + "/" + cxServerName + "/" + cxServerAuditService;
        LOGGER.info("business service url:"+url);
        return url;
    }
}
