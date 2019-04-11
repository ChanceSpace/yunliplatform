<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>绿色共享运力平台</title><meta charset="UTF-8" />
    <!-- HTTP 1.1 -->
    <meta http-equiv="pragma" content="no-cache">
    <!-- HTTP 1.0 -->
    <meta http-equiv="cache-control" content="no-cache">
    <link rel="Bookmark" href="${pageContext.request.contextPath}/build/images/favicon.png">
    <link rel="Shortcut Icon" href="${pageContext.request.contextPath}/build/images/favicon.png">
    <title><decorator:title default="绿色共享运力平台"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <decorator:head/>
</head>

<body>
<decorator:body/>

</body>
</html>