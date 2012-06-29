package com.feature.resources.server.servlet;

import com.feature.resources.server.service.GraphicService;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: ZouYanjian
 * Date: 12-6-5
 * Time: 下午2:01
 * FileName:URIServlet
 */
@Singleton
@WebServlet(asyncSupported = false, name = "URIServlet", urlPatterns = {"/servlet/menu"})
public class URIServlet extends HttpServlet {
    @Inject
    GraphicService graphicService;

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse response) throws ServletException, IOException {
        String type = httpServletRequest.getParameter("type");
        httpServletRequest.getRequestDispatcher("/graphic.jsp?type="+type).forward(httpServletRequest, response);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(httpServletRequest, response);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
