package com.feature.resources.server.servlet;

import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: ZouYanjian
 * Date: 12-7-3
 * Time: 上午11:41
 * FileName:LoginServerlet
 */
@Singleton
public class LoginServerlet extends HttpServlet {
    public static final Logger LOGGER = LoggerFactory.getLogger(LoginServerlet.class);
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("User Login !!!");
        resp.sendRedirect("../main.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }
}
