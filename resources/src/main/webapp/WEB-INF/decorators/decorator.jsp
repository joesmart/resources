<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <title><decorator:title default="Mysterious page..."/></title>

    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <meta http-equiv="Cache-Control" content="no-store"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="0"/>

    <link href="${ctx}/static/bootstrap/2.0.3/css/bootstrap.css" type="text/css" rel="stylesheet"/>
    <link href="${ctx}/static/bootstrap/2.0.3/css/bootstrap-responsive.css" type="text/css" rel="stylesheet"/>
    <link href="${ctx}/static/jquery-validation/1.9.0/validate.css" type="text/css" rel="stylesheet"/>
    <link href="${ctx}/static/main.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/static/jquery-ui-1.8.20/css/ui-lightness/jquery-ui-1.8.20.custom.css" type="text/css"
          rel="stylesheet"/>
    <script src="${ctx}/static/bootstrap/2.0.3/js/bootstrap.js" type="text/javascript"></script>
    <%--<script src="${ctx}/static/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>--%>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
    <script src="${ctx}/static/jquery-validation/1.9.0/jquery.validate.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/jquery-validation/1.9.0/messages_cn.js" type="text/javascript"></script>
    <script src="${ctx}/static/jquery-ui-1.8.20/js/jquery-ui-1.8.20.custom.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/plugin/jquery.pubsub.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/plugin/tables-my.js" type="text/javascript"></script>

    <decorator:head/>
</head>

<body data-spy="scroll" data-target=".subnav" data-offset="50">
<!-- Navbar
    ================================================== -->
<div class="navbar navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container">
            <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </a>

            <div class="nav-collapse collapse">

            </div>
            <div class="pull-right">
                <h3><a href="${ctx}/logout" class="">登出</a></h3>
            </div>
        </div>
    </div>
</div>

<div class="container container-height">
    <header class="jumbotron subhead" id="overview">
        <div class="subnav">
            <ul class="nav nav-pills">
                <li><a href="${ctx}/index.jsp">首页</a></li>
                <li><a href="${ctx}/servlet/menu?type=all">图片</a></li>
                <li><a href="#">文档</a></li>
                <li><a href="#">视频</a></li>
                <li><a href="${ctx}/upload/upload.jsp">上传</a></li>
                <li><a href="${ctx}/other/createWorkspace.jsp">其他</a></li>
            </ul>
        </div>
    </header>

    <div class="container-fluid">
        <div class="row-fluid">
            <div class="span2 well leftpanel-height">
                <page:applyDecorator page="/menu.jsp" name="left-panel"/>
            </div>
            <div class="span10 well rightpanel-height"id="main_panel">
                <decorator:body/>
            </div>
        </div>
    </div>
    <footer>
        <p>&copy; Company 2012</p>
    </footer>
</div>
</body>
</html>