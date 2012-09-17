<%--
  Created by IntelliJ IDEA.
  User: ZouYanjian
  Date: 12-7-3
  Time: 上午10:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter" %>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException" %>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>登录界面</title>
</head>
<body>
<div class="container well">
    <form id="loginForm" action="login.jsp" method="post">
        <%
            String error = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
            if (error != null) {
        %>
        <div class="control-group">
            <div class="controls ">
                <div class="alert alert-error">
                    <button class="close" data-dismiss="alert">×</button>
                    登录失败，请重试.
                </div>
            </div>
        </div>

        <%
            }
        %>

        <div class="control-group">
            <label for="username" class="control-label">名称:</label>

            <div class="controls">
                <input type="text" id="username" name="username" size="50" value="${username}" class="required span2"/>
            </div>
        </div>
        <div class="control-group">
            <label for="password" class="control-label">密码:</label>

            <div class="controls">
                <input type="password" id="password" name="password" size="50" class="required span2"/>
            </div>
        </div>
        <div class="control-group">
            <div class="controls">
                <label class="checkbox inline" for="rememberMe">
                    <input type="checkbox" id="rememberMe" name="rememberMe"/> 记住我</label>
                <br/>
                <br/>
                <input id="submit" class="btn" type="submit" value="登录"/>
                &nbsp;
                <a href="register.jsp" class="btn">注册</a>

            </div>
        </div>

    </form>
</div>
</body>
</html>