<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>(尚筹网)后台管理</title>
    <%@ include file="/WEB-INF/include/resources.jsp" %>
    <script>
        $(function () {
            $('#assignRoleList').click(() => $("#unAssignedRoleList>option:selected").appendTo("#assignedRoleList"))
            $('#unAssignRoleList').click(() => $("#assignedRoleList>option:selected").appendTo("#unAssignedRoleList"))
            $('#saveRoleAdminRelationBtn').click(() => $("#assignedRoleList>option").prop('selected', 'selected'))
        })
    </script>
</head>
<body>
<%@ include file="/WEB-INF/include/nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@ include file="/WEB-INF/include/sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="#">首页</a></li>
                <li><a href="#">数据列表</a></li>
                <li class="active">分配角色</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-body">
                    <form action="assign/role/to/admin/save" method="post" role="form" class="form-inline">
                        <input type="hidden" name="adminId" value="${requestScope.adminId}" />
                        <input type="hidden" name="pageNum" value="${param.pageNum}" />
                        <input type="hidden" name="keyword" value="${param.keyword}" />
                        <div class="form-group">
                            <label for="unAssignedRoleList">未分配角色列表</label><br>
                            <select id="unAssignedRoleList" class="form-control" multiple size="10" style="width:100px;overflow-y:auto;">
                                <c:forEach items="${requestScope.unAssignedRoleList}" var="role">
                                    <option value="${role.id}">${role.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <ul>
                                <li id="assignRoleList" class="btn btn-default glyphicon glyphicon-chevron-right"></li>
                                <br>
                                <li id="unAssignRoleList" class="btn btn-default glyphicon glyphicon-chevron-left"
                                    style="margin-top:20px;"></li>
                            </ul>
                        </div>
                        <div class="form-group" style="margin-left:40px;">
                            <label for="assignedRoleList">已分配角色列表</label><br>
                            <select id="assignedRoleList" name="assignedRoleList" class="form-control" multiple size="10" style="width:100px;overflow-y:auto;">
                                <c:forEach items="${requestScope.assignedRoleList}" var="role">
                                    <option value="${role.id}">${role.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <button id="saveRoleAdminRelationBtn" type="submit">保存</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
</script>
</body>
</html>
