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

    <h2 class="w-1/2 mx-auto text-4xl text-center mt-4">尚筹网系统消息</h2>

    <h3 class="w-1/2 mx-auto text-3xl text-center mt-4">${requestScope.exception.message}</h3>

    <%-- TODO: 少一个回去的按钮 --%>
</div>
<script src="jquery/jquery-2.1.1.min.js" type="text/javascript"></script>
<script src="bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
</body>
</html>
