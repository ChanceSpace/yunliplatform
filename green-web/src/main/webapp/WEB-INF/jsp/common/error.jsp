<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="yj" uri="http://www.rajaev.com" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>绿色共享运力平台</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/layui/css/layui.css" media="all"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />

</head>
<body>

<div class="nofind_container layui-container">
    <h1 class="nofind_title">抱歉，您访问的页面无法显示!</h1>

    <div class="nofind_box layui-row">
        <div class="layui-col-md8">
            <img src="${pageContext.request.contextPath}/build/images/error.jpg" width="70%"/>
        </div>
        <div class="layui-col-md4">
            <p>可能因为网络异常或链接已更改:</p>

            <p>>> 请联系系统管理员 68389838转6112</p>
        </div>
    </div>
</div>

</body>
</html>
