package com.feature.resources.server.servlet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

/**
 * User: ZouYanjian
 * Date: 12-7-12
 * Time: 下午2:44
 * FileName:URIServletTest
 */
public class URIServletTest {

    private URIServlet uriServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;

    @Before
    public void setUp() throws Exception {
        uriServlet = new URIServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void should_execute_doPost_method() throws IOException, ServletException {
        String type = "all";
        when(request.getParameter("type")).thenReturn(type);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class) ;
        when(request.getRequestDispatcher("/graphic.jsp?type=all")).thenReturn(requestDispatcher);
        uriServlet.doPost(request, response);
        verify(request).getRequestDispatcher("/graphic.jsp?type=" + type);
        verify(requestDispatcher).forward(request, response);


    }
}
