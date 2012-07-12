package com.feature.resources.server.servlet;

import com.feature.resources.server.dto.UserDTO;
import com.feature.resources.server.service.UserService;
import com.feature.resources.server.service.impl.UserServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * User: ZouYanjian
 * Date: 12-7-12
 * Time: 下午2:51
 * FileName:RegisterServletTest
 */
public class RegisterServletTest {

    private RegisterServlet registerServlet;
    private UserService userService;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher requestDispatcher;

    @Before
    public void setUp() {
        registerServlet = new RegisterServlet();
        userService = mock(UserServiceImpl.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        registerServlet.setUserService(userService);
        requestDispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/login.jsp")).thenReturn(requestDispatcher);

    }

    @After
    public void tearDown() {

    }

    @Test
    public void should_execute_doPostMethod_successful() throws IOException, ServletException {
        when(request.getParameter(anyString())).thenReturn("test");
        when(userService.isUserAlreadyExists("test")).thenReturn(false);
        when(userService.registerUser(any(UserDTO.class))).thenReturn(true);

        registerServlet.doPost(request, response);

        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void should_get_password_not_match_erro_when_password1_not_match_password2() throws IOException, ServletException {
        when(request.getRequestDispatcher("/register.jsp")).thenReturn(requestDispatcher);
        when(request.getParameter(anyString())).thenReturn("test");
        when(request.getParameter("password1")).thenReturn("12312");
        when(request.getParameter("password2")).thenReturn("13212");
        when(userService.isUserAlreadyExists("test")).thenReturn(false);
        when(userService.registerUser(any(UserDTO.class))).thenReturn(true);

        registerServlet.doPost(request, response);

        verify(request).setAttribute("errorMessage","输入密码不一致!");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void should_get_password_not_match_erro_when_user_already_registered() throws IOException, ServletException {
        when(request.getRequestDispatcher("/register.jsp")).thenReturn(requestDispatcher);
        when(request.getParameter(anyString())).thenReturn("test");
        when(userService.isUserAlreadyExists("test")).thenReturn(true);
        when(userService.registerUser(any(UserDTO.class))).thenReturn(true);

        registerServlet.doPost(request, response);

        verify(request).setAttribute("errorMessage","登录名已经注册!");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void should_get_password_not_match_erro_when_user_data_save_failed() throws IOException, ServletException {
        when(request.getRequestDispatcher("/register.jsp")).thenReturn(requestDispatcher);
        when(request.getParameter(anyString())).thenReturn("test");
        when(userService.isUserAlreadyExists("test")).thenReturn(false);
        when(userService.registerUser(any(UserDTO.class))).thenReturn(false);

        registerServlet.doPost(request, response);

        verify(request).setAttribute("errorMessage","注册失败!");
        verify(requestDispatcher).forward(request, response);
    }

}
