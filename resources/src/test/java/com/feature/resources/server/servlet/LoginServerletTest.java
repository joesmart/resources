package com.feature.resources.server.servlet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * User: ZouYanjian
 * Date: 12-7-12
 * Time: 下午2:38
 * FileName:LoginServerletTest
 */
public class LoginServerletTest {

    private LoginServerlet loginServerlet;
    private HttpServletResponse response;
    private HttpServletRequest request;

    @Before
    public void setUp() throws Exception {
        loginServerlet = new LoginServerlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void should_execute_doPost_method_successful(){

        try {
            loginServerlet.doPost(request,response);
            verify(response).sendRedirect("../main.jsp");
        } catch (ServletException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Test
    public void should_execute_doGet_method_successful(){

        try {
            loginServerlet.doGet(request,response);
            verify(response).sendRedirect("../main.jsp");
        } catch (ServletException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


}
