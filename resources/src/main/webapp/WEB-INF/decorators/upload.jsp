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

    <!-- Generic page styles -->
    <link rel="stylesheet" href="../static/upload/css/style.css">
    <!-- Bootstrap CSS fixes for IE6 -->
    <!--[if lt IE 7]><link rel="stylesheet" href="http://blueimp.github.com/cdn/css/bootstrap-ie6.min.css"><![endif]-->
    <!-- Bootstrap Image Gallery styles -->
    <link rel="stylesheet" href="http://blueimp.github.com/Bootstrap-Image-Gallery/css/bootstrap-image-gallery.min.css">
    <!-- CSS to style the file input field as button and adjust the Bootstrap progress bars -->
    <link rel="stylesheet" href="../static/upload/css/jquery.fileupload-ui.css">
    <!-- Shim to make HTML5 elements usable in older Internet Explorer versions -->
    <!--[if lt IE 9]><script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->

    <script src="${ctx}/static/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/jquery-validation/1.9.0/jquery.validate.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/jquery-validation/1.9.0/messages_cn.js" type="text/javascript"></script>
    <script src="${ctx}/static/jquery-ui-1.8.20/js/jquery-ui-1.8.20.custom.min.js" type="text/javascript"></script>


    <!-- The jQuery UI widget factory, can be omitted if jQuery UI is already included -->
    <script src="../static/upload/js/vendor/jquery.ui.widget.js"></script>
    <!-- The Templates plugin is included to render the upload/download listings -->
    <script src="http://blueimp.github.com/JavaScript-Templates/tmpl.min.js"></script>
    <!-- The Load Image plugin is included for the preview images and image resizing functionality -->
    <script src="http://blueimp.github.com/JavaScript-Load-Image/load-image.min.js"></script>
    <!-- The Canvas to Blob plugin is included for image resizing functionality -->
    <script src="http://blueimp.github.com/JavaScript-Canvas-to-Blob/canvas-to-blob.min.js"></script>

    <script src="http://blueimp.github.com/Bootstrap-Image-Gallery/js/bootstrap-image-gallery.min.js"></script>
    <!-- The Iframe Transport is required for browsers without support for XHR file uploads -->
    <script src="../static/upload/js/jquery.iframe-transport.js"></script>
    <!-- The basic File Upload plugin -->
    <script src="../static/upload/js/jquery.fileupload.js"></script>
    <!-- The File Upload file processing plugin -->
    <script src="../static/upload/js/jquery.fileupload-fp.js"></script>
    <!-- The File Upload user interface plugin -->
    <script src="../static/upload/js/jquery.fileupload-ui.js"></script>
    <!-- The localization script -->
    <script src="../static/upload/js/locale.js"></script>
    <!-- The main application script -->
    <script src="../static/upload/js/main.js"></script>
    <!-- The XDomainRequest Transport is included for cross-domain file deletion for IE8+ -->
    <!--[if gte IE 8]><script src="../static/upload/js/cors/jquery.xdr-transport.js"></script><![endif]-->
    <script src="../static/bootstrap/2.0.3/js/bootstrap.js" type="text/javascript"></script>
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
        </div>
    </div>
</div>

<div class="container container-height">
    <header class="jumbotron subhead" id="overview">

        <div class="subnav">
            <ul class="nav nav-pills">
                <li><a href="${ctx}/index.jsp">首页</a></li>
                <li><a href="${ctx}/servlet/menu">图片</a></li>
                <li><a href="#">文档</a></li>
                <li><a href="#">视频</a></li>
                <li><a href="${ctx}/upload/upload.jsp">上传</a></li>
                <li><a href="">其他</a></li>
            </ul>
        </div>
    </header>

    <div class="container  leftpanel-height">
        <decorator:body/>
    </div>

    <footer>
        <p>&copy; Company 2012</p>
    </footer>
</div>
</body>
</html>