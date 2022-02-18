<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>(尚筹网)后台管理</title>
    <%@ include file="/WEB-INF/include/resources.jsp" %>
    <link rel="stylesheet" href="css/pagination.css">
    <script type="text/javascript" src="jquery/jquery.pagination.js"></script>
    <script type="text/javascript">
        // 初始化分页组件
        function initPagination() {
            // 总页数
            var num_entries = ${requestScope.pageInfo.total};
            // 创建分页
            $("#Pagination").pagination(num_entries, {
                num_edge_entries: 2, //边缘页数
                num_display_entries: 4, //主体页数
                callback: pageSelectCallback, // 回调函数
                items_per_page: ${requestScope.pageInfo.pageSize}, //每页显示项数
                current_page: ${requestScope.pageInfo.pageNum} -1, // 当前页码
                prev_text: "上一页",
                next_text: "下一页"
            });
        }

        // 点击页码后的回调函数
        function pageSelectCallback(page_index) {
            window.location.href = "admin/page?pageNum=" + (page_index + 1) + "&keyword=${param.keyword}";
            return false;
        }

        $(function () {
            initPagination()
        })
    </script>
</head>
<body>
<%@ include file="/WEB-INF/include/nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@ include file="/WEB-INF/include/sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" action="admin/page" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input name="keyword" value="${param.keyword}"
                                       class="form-control has-success" type="text" placeholder="请输入查询条件"/>
                            </div>
                        </div>
                        <button type="submit" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <button type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i
                            class=" glyphicon glyphicon-remove"></i> 删除
                    </button>
                    <a href="admin/to/add" class="btn btn-primary" style="float:right;">
                        <i class="glyphicon glyphicon-plus"></i> 新增
                    </a>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input type="checkbox"></th>
                                <th>账号</th>
                                <th>名称</th>
                                <th>邮箱地址</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:if test="${empty requestScope.pageInfo.list}">
                                <tr>
                                    <td colspan="6" align="center">暂时没有数据哟</td>
                                </tr>
                            </c:if>
                            <c:if test="${!empty requestScope.pageInfo.list}">
                                <c:forEach items="${requestScope.pageInfo.list}" var="admin" varStatus="infoStatus">
                                    <tr>
                                        <td>${infoStatus.count}</td>
                                        <td><input type="checkbox"/></td>
                                        <td>${admin.loginAcct}</td>
                                        <td>${admin.userName}</td>
                                        <td>${admin.email}</td>
                                        <td>
                                            <a href="assign/role/to/admin/${admin.id}/page?pageNum=${requestScope.pageInfo.pageNum}&keyword=${param.keyword}" class="btn btn-success btn-xs">
                                                <i class="glyphicon glyphicon-check"></i>
                                            </a>
                                            <a href="admin/to/edit/${admin.id}?pageNum=${requestScope.pageInfo.pageNum}&keyword=${param.keyword}" class="btn btn-primary btn-xs">
                                                <i class="glyphicon glyphicon-pencil"></i>
                                            </a>
                                            <a href="admin/remove/${admin.id}?pageNum=${requestScope.pageInfo.pageNum}&keyword=${param.keyword}"
                                               class="btn btn-danger btn-xs">
                                                <i class="glyphicon glyphicon-remove"></i>
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <div id="Pagination" class="pagination"></div>
                                </td>
                            </tr>

                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
</script>
</body>
</html>
