<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<div id="resources_menu" >
    <ul>
        <li><a style="width: 80px" href="${ctx}/servlet/menu" class="btn btn-primary btn-large btn">全部</a></li><br/>
        <li><a style="width: 80px" href="${ctx}/servlet/menu" class="btn btn-primary btn-large btn">未审核</a></li><br/>
        <li><a style="width: 80px" href="${ctx}/servlet/menu" class="btn btn-primary btn-large btn">最近上传</a></li><br/>
    </ul>
</div>