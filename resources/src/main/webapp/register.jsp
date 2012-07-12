<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="error" value="${requestScope.errorMessage}"/>

<html>
<head>
    <title>注册</title>
</head>
<body>
<div class="container well">
    <form id="registerForm" action="servlet/register" method="post">
        <c:if test="${error != null}">

        <div class="control-group">
            <div class="controls ">
                <div class="alert alert-error">
                    <button class="close" data-dismiss="alert">×</button>
                    ${errorMessage},注册失败!.
                </div>
            </div>
        </div>
        </c:if>

        <div class="control-group">
            <label for="userName" class="control-label">姓名:</label>

            <div class="controls">
                <input type="text" id="userName" name="userName" size="50" value="${userName}" class="required span2"/>
            </div>
        </div>

        <div class="control-group">
            <label for="loginName" class="control-label">登录用户名:</label>

            <div class="controls">
                <input type="text" id="loginName" name="loginName" size="50" value="${loginName}" class="required span2"/>
            </div>
        </div>


        <div class="control-group">
            <label for="email" class="control-label">email:</label>

            <div class="controls">
                <input type="text" id="email" name="email" size="50" value="${email}" class="required span2"/>
            </div>
        </div>

        <div class="control-group">
            <label for="password1" class="control-label">密码:</label>

            <div class="controls">
                <input type="password" id="password1" name="password1" size="50" class="required span2"/>
            </div>
        </div>

        <div class="control-group">
            <label for="password2" class="control-label">确认密码:</label>

            <div class="controls">
                <input type="password" id="password2" name="password2" size="50" class="required span2"/>
            </div>
        </div>

        <div class="control-group">
            <div class="controls">
                <input id="submit" class="btn" type="submit" value="注册"/>
                <a href="login.jsp" class="btn" >取消</a>
            </div>
        </div>

    </form>
</div>
</body>
</html>