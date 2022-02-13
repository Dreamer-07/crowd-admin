<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>(尚筹网)后台管理</title>
    <%@ include file="/WEB-INF/include/resources.jsp" %>
    <style>
    </style>
</head>
<body>
<%@ include file="/WEB-INF/include/nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@ include file="/WEB-INF/include/sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="main">首页</a></li>
                <li><a href="admin/page">数据列表</a></li>
                <li class="active">新增</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
                <div class="panel-body">
                    <p>${requestScope.exception.message}</p>
                    <form role="form" action="admin/add" method="post">
                        <div class="form-group">
                            <label for="loginAcct">登陆账号</label>
                            <input type="text" class="form-control" id="loginAcct" name="loginAcct" placeholder="请输入登陆账号">
                        </div>
                        <div class="form-group">
                            <label for="password">登录密码</label>
                            <input type="text" class="form-control" id="password" name="userPswd" placeholder="请输入登录密码">
                        </div>
                        <div class="form-group">
                            <label for="username">用户名称</label>
                            <input type="text" class="form-control" id="username" name="userName" placeholder="请输入用户名称">
                        </div>
                        <div class="form-group">
                            <label for="email">邮箱地址</label>
                            <input type="email" class="form-control" id="email" name="email" placeholder="请输入邮箱地址">
                            <p class="help-block label label-warning">请输入合法的邮箱地址, 格式为： xxxx@xxxx.com</p>
                        </div>
                        <button type="submit" class="btn btn-success"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                        <button type="reset" class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 重置</button>
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
