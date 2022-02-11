<%--
  Created by IntelliJ IDEA.
  User: 23911
  Date: 2022/2/11
  Time: 12:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="keys" content="">
    <meta name="author" content="">
    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/"/>
    <link rel="stylesheet" href="tailwind/tailwind.min.css">
    <style>

    </style>
</head>
<body class="p-0">
<nav class="w-full h-15 bg-black text-white text-opacity-60 " role="navigation">
    <div class="w-3/4 mx-auto">
        <div class="navbar-header">
            <div><a class="navbar-brand" href="index.html" style="font-size:32px;">尚筹网-创意产品众筹平台</a></div>
        </div>
    </div>
</nav>

<div class="w-3/4 mx-auto">

    <form class="w-1/5 mx-auto" role="form">
        <h2 class="text-3xl mt-4 mb-1.5"><i class="glyphicon glyphicon-log-in"></i> 管理员登录</h2>
        <div>
            <input type="text"
                   class="w-full outline-none border border-green-900 rounded-sm p-1.5 focus:ring-1 focus:ring-green-400"
                   id="username" name="username" placeholder="请输入登录账号" autofocus>
            <span class="glyphicon glyphicon-user form-control-feedback"></span>
        </div>
        <div>
            <input type="password"
                   class="w-full outline-none border border-green-900 rounded-sm p-1.5 focus:ring-1 focus:ring-green-400 mt-2.5"
                   id="password" name="password" placeholder="请输入登录密码">
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
        </div>
        <a class="block w-full leading-8 box-content mt-2.5 py-1 text-lg bg-green-500 rounded-md text-center text-white" href="main.html">登录</a>
    </form>
</div>
<script src="jquery/jquery-2.1.1.min.js" type="text/javascript"></script>
<script src="bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
</body>
</html>
