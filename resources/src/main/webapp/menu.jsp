<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<div id="resources_menu" >
    <a style="width: 80px" href="${ctx}/servlet/menu" class="btn btn-primary btn-large btn">全部</a>
    <br/>
    <br/>
    <a style="width: 80px" href="${ctx}/servlet/menu" class="btn btn-primary btn-large btn">未审核</a>
    <br/>
    <br/>
    <a style="width: 80px" href="${ctx}/servlet/menu" class="btn btn-primary btn-large btn">最近上传</a>
</div>