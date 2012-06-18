<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<div id="resources_menu" >
    <a style="width: 100px" href="${ctx}/other/createWorkspace.jsp" class="btn btn-primary btn-large btn">创建工作空间</a>
    <br/>
    <br/>
    <a style="width: 100px" href="${ctx}/other/createTag.jsp" class="btn btn-primary btn-large btn">创建新标签</a>

</div>