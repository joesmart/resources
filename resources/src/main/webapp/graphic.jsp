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
    <input type="hidden" id="record_size" value="${fn:length(graphics)}"/>

    <table id="dataTable" class="table table-bordered  table-striped">
        <thead>
            <tr>
                <th width="5%"><input type="checkbox"/></th>
                <th width="5%">状态</th>
                <th width="15%">缩略图</th>
                <th width="40%">文件名</th>
                <th width="20%">修改日期</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="graphic" items="${requestScope.graphics}" varStatus="status">
            <tr>
                <td><input type="checkbox"></td>
                <td><i class="icon-star-empty"></i></td>
                <td><img src="../rs/graphics?id=${graphic.id}&size=30X30" class="thumbnail_img"/> </td>
                <td><c:out value="${graphic.name}"/></td>
                <td>
                    <fmt:formatDate  value="${graphic.updateDate}" type="both" pattern="yy-MM-dd HH:mm:ss" /><br/>
                </td>
                <td>
                    <a href="#" id='<c:out value="${graphic.id}" />' class="detail_link">详情</a>
                    <a href="#" id='<c:out value="${graphic.id}" />' class="edit_link">编辑</a>
                    <a href="#" id='<c:out value="${graphic.id}" />' class="delete_link">删除</a>
                    <a href="#" id='<c:out value="${graphic.id}" />' class="check_link">审核</a>
                </td>
            </tr>
            </c:forEach>
        </tbody>
    </table>
    <div id="pagination">

    </div>

    <div id="detail_panel" class="container-fluid">
        <div class="row-fluid">
            <div class="span6">
                <img class="detail_image" src="" alt="" />
            </div>
            <div id="main_info"class="span6">
                adfadfadf
            </div>
            <div id="properties_info" class="span6" style="display: none">
                xxxxxx
            </div>
        </div>
    </div>
</body>
</html>