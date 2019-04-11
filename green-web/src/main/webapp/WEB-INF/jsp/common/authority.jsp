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
<body class="authority_container">
    <div class="nofind_container layui-container">
      <div class="authority_box layui-row">
        <div class="authority_left layui-col-md6">
            <img src="${pageContext.request.contextPath}/build/images/authority1.png"/>
        </div>
        <div class="authority_right layui-col-md6">
            <img src="${pageContext.request.contextPath}/build/images/authority2.png"/>
            <div class="authority_right_box">
                <p>很抱歉，你没有权限进行该操作</p>
            </div>
        </div>
      </div>
</body>

</html>
