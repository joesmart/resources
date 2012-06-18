<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="request" value="${pageContext.request}"/>
<c:set var="graphics" value="${requestScope.graphics}"/>

<html>
<head>
    <title>资源列表</title>
    <script src="${ctx}/static/main.js" type="text/javascript"></script>
</head>
<body>

    <div id="dataTable" class="container" style="width: 900px;">

    </div>

    <div id="detail_panel" class="container-fluid" style="display:none;">
        <div class="row-fluid">
            <div class="span6">
                <img class="detail_image" src="" alt="" />
            </div>
            <div id="main_info"class="span6">
                <form>
                    <label>图片名称:</label>
                    <input type="text" id="name"/>
                    <label>描述:</label>
                    <textarea rows="4" cols="10" id="description"></textarea>
                    <br/>
                    <a id="properties" href="#" class="btn btn-large"><i class="icon-forward"></i>属性详情</a>
                </form>
                <br/>
                <button id="save_button" class="btn btn-large" style="display: none;">保存</button>
            </div>
            <div id="properties_info" class="span6" style="display: none">
                <h3>属性列表</h3>
                <br/>
                <form>
                    <label>UUID</label>
                    <span id="uuid"></span>
                    <label>储存路径:</label>
                    <span id="path"></span>
                    <label>大小:</label>
                    <span id="size"></span>
                    <label>创建时间:</label>
                    <span id="createTime"></span>
                    <br/>
                    <br/>
                    <a id="back" href="#" class="btn btn-large"><i class="icon-backward"></i>返回</a>
                </form>
            </div>
        </div>
    </div>
</body>
</html>