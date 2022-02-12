<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">
<head>
    <title>(尚筹网)后台管理登录</title>
    <link rel="stylesheet" href="tailwind/tailwind.min.css">
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

    <form action="admin/login" method="post" class="w-1/5 mx-auto" role="form">
        <h2 class="text-3xl mt-4 mb-1.5"><i class="glyphicon glyphicon-log-in"></i> 管理员登录</h2>
        <p class="text-red-400">${requestScope.exception.message}</p>
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
        <input type="submit" class="block w-full leading-8 box-content mt-2.5 py-1 text-lg bg-green-500 rounded-md text-center text-white" value="登录" />
    </form>
</div>

</body>
</html>
