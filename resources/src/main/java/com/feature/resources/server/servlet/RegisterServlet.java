package com.feature.resources.server.servlet;

import com.feature.resources.server.dto.UserDTO;
import com.feature.resources.server.service.UserService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.method.MethodConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.IOException;

/**
 * User: ZouYanjian
 * Date: 12-7-5
 * Time: 上午11:01
 * FileName:RegisterServlet
 */
@Singleton
public class RegisterServlet extends HttpServlet {
    public static final Logger LOGGER = LoggerFactory.getLogger(RegisterServlet.class);

    @Inject
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("userName");
        String loginName = req.getParameter("loginName");
        String password1 = req.getParameter("password1");
        String password2 = req.getParameter("password2");
        String email = req.getParameter("email");
        String erroMessage = null;
        if(!password1.equals(password2)){
            erroMessage = "输入密码不一致!";
        }

        try {
            validate(userName,loginName,email,password1);
        } catch (MethodConstraintViolationException e) {

            erroMessage = "输入参数长度不对";
        }

        if(userService.isUserAlreadyExists(loginName)){
            erroMessage = "登录名已经注册!";
        }

        if(StringUtils.isNotEmpty(erroMessage)){
            req.setAttribute("errorMessage",erroMessage);
        }else {
            UserDTO userDTO = new UserDTO();
            userDTO.setEmail(email);
            userDTO.setLoginName(loginName);
            userDTO.setPassword(password1);
            userDTO.setName(userName);
            if(!userService.registerUser(userDTO)){
                erroMessage = "注册失败!";
                req.setAttribute("errorMessage",erroMessage);
            }
        }
        LOGGER.info("userName:"+userName +" loginName:"+loginName + " password:"+password1 + " email:"+ email );
        if(StringUtils.isEmpty(erroMessage)){
            req.getRequestDispatcher("/login.jsp").forward(req,resp);
        }else {
            req.getRequestDispatcher("/register.jsp").forward(req,resp);
        }
    }

    public @Valid void  validate(@Min(5) @Max(30) String userName,
                                   @Min(5) @Max(30) String loginName,
                                   @Min(5) @Max(30) @Email String email,
                                   @Min(5) @Max(30) String password
                                   ){

    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
